package game.battle;

import engine.window.MyWindow;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;

public class BattleView {

    private static final int FOIL_PAD = 1,
                             BAR_PAD = 2, BAR_W = 60, BAR_H = 8,
                             CHAR_W = 90, CHAR_H = 120;
    private static final int CP_Y_LEN = 200, CP_X_OFFSET = MyWindow.getCanvas().getWidth()/4;

    private Battle battle;

    BattleView() {

    }
    BattleView(Battle battle) {
        this.battle = battle;
    }

    void updateBattleCharacters(ArrayList<Battle.BattleCharacter> team) {
        for(var bc : team) {
            bc.healthRatio = (double) bc.character.currentHealth / bc.character.maxHealth;
            bc.staminaRatio = (double) bc.character.currentStamina / bc.character.maxStamina;
            bc.manaRatio = (double) bc.character.currentMana / bc.character.maxMana;
        }
    }

    void updateAllBattleCharacters() {
        updateBattleCharacters(battle.teamAlly);
        updateBattleCharacters(battle.teamEnemy);
    }

    private static void drawRatioBar(BufferStrategy bs, Color c, int x, int y, double ratio) {
        x -= BAR_W/4 - BAR_W/2;
        y -= BAR_H*4;
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(x - FOIL_PAD, y - FOIL_PAD, BAR_W + FOIL_PAD * 2, BAR_H + FOIL_PAD * 2);
        g.setColor(new Color(c.getRed()/5, c.getGreen()/5, c.getBlue()/5));
        g.fillRect(x, y, BAR_W, BAR_H);
        g.setColor(c);
        g.fillRect(x, y, (int) (BAR_W * ratio), BAR_H);
        g.dispose();
    }

    public void processBattleCharacterViewPos(ArrayList<Battle.BattleCharacter> team, int i) {
        for(int ci = 0; ci < team.size(); ci++) {
            Battle.BattleCharacter bc = team.get(ci);
            if (bc != null) {
                int sgn = 2 * (i % 2) - 1;
                int len = CP_Y_LEN / (team.size() + 1);
                bc.x = MyWindow.getCanvas().getWidth() / 2 + sgn * CP_X_OFFSET - CHAR_W/2 + (CP_X_OFFSET/4) * (team.size() - ci - 1);
                bc.y = MyWindow.getCanvas().getHeight() - CHAR_H - ci * len - len/2;
            }
        }
    }

    public void processAllBattleCharacterViewPos() {
        processBattleCharacterViewPos(battle.teamAlly, 0);
        processBattleCharacterViewPos(battle.teamEnemy, 1);
    }

    private void renderTeamBars(BufferStrategy bs, ArrayList<Battle.BattleCharacter> team) {
        for(var bcv : team) {
            if (bcv != null) {
                drawRatioBar(bs, Color.red, bcv.x, bcv.y, bcv.healthRatio);
                drawRatioBar(bs, Color.green, bcv.x, bcv.y + BAR_PAD + BAR_H, bcv.staminaRatio);
                drawRatioBar(bs, Color.blue, bcv.x, bcv.y + (BAR_PAD + BAR_H) * 2, bcv.manaRatio);
            }
        }
    }

    private void renderTeam(BufferStrategy bs, ArrayList<Battle.BattleCharacter> team) {
        for(var bc : team) {
            if (bc != null) {
                //Draw character image to canvas
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                BufferedImage frame;
                if((frame = bc.animation.getFrame()) != null) {
                    try {
                        if(bc.ally) {
                            g.drawImage(frame, bc.x, bc.y, CHAR_W, CHAR_H, null);
                        } else {
                            g.drawImage(frame, bc.x + CHAR_W, bc.y, -CHAR_W, CHAR_H, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    g.setColor(Color.lightGray);
                    g.fillRect(bc.x, bc.y, CHAR_W, CHAR_H);
                }
                g.dispose();
            }
        }
    }

    void render(BufferStrategy bs) {
        int winWidth = MyWindow.getCanvas().getWidth();
        int winHeight = MyWindow.getCanvas().getHeight();
        Graphics2D g;

        {  // background
            g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(new Color(100, 150, 250));
            g.fillRect(0, 0, winWidth, winHeight);
            g.setColor(new Color(100, 150, 80));
            g.fillRect(0, winHeight * 2 / 3, winWidth, winHeight);
            g.dispose();
        }

        {  // characters
            renderTeam(bs, battle.teamAlly);
            renderTeam(bs, battle.teamEnemy);
        }

        {  // foreground
            g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(new Color(250, 240, 0, 40));
            g.fillRect(0, 0, winWidth, winHeight);
            g.setColor(new Color(5, 15, 255, 15));
            g.fillOval(0, 0, winWidth, winHeight);
            g.dispose();
        }

        {  // ui
            renderTeamBars(bs, battle.teamAlly);
            renderTeamBars(bs, battle.teamEnemy);
        }
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
