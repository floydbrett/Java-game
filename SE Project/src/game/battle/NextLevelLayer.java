package game.battle;
import engine.audio.MyAudioPlayer;
import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerImplement;
import engine.window.MyWindow;
import engine.utils.MainMenuLayer;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import engine.layers.LayerController;
import engine.layers.LayerController;
import game.main.Game;
import game.main.GameLayer;

import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class NextLevelLayer implements LayerImplement{
    private final Layer layer = new Layer(this, this, this);

    // menu data
    private String[] buttons;
    private int buttonIndex = 0;
    protected int buttonWidth = 50, buttonHeight = 20, buttonBuffer = 5;
    protected int x;
    protected int y;
    // battle data


    public NextLevelLayer() {
        layer.setName("NextLevelLayer");
        MyAudioPlayer.play("res/audio/NextLevel.wav");
        layer.setBlocks(false, true, false);
        buttons = new String[]{"Next Level", "Main Menu", "Exit Game"};
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case UP:
                if(buttonIndex == 0){
                    buttonIndex = buttons.length - 1;
                }
                else{
                    buttonIndex = (buttonIndex - 1) % buttons.length;
                }
                break;
            case LEFT:
            case DOWN:
                buttonIndex = (buttonIndex + 1) % buttons.length;
                break;
            case RIGHT:
            case START:
                LayerController.clearLayers();
                LayerController.pushLayer((new MainMenuLayer()).getLayer());
                break;
            case A:
                buttonPressed(buttons[buttonIndex]);
                break;
        }
    }

    public void buttonPressed(String buttonName) {
        if(buttonName.equals("Next Level")) {
            LayerController.popLayer();
        }
        else if(buttonName.equals("Main Menu")) {
            LayerController.clearLayers();
            LayerController.pushLayer((new MainMenuLayer()).getLayer());
        }
        else if(buttonName.equals("Exit Game")) {
            MyWindow.close();
        }
    }

    @Override
    public void render(BufferStrategy bs) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        int winWidth = MyWindow.getCanvas().getWidth();
        int winHeight = MyWindow.getCanvas().getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, MyWindow.getCanvas().getWidth(), MyWindow.getCanvas().getHeight());
        g.setColor(Color.WHITE);
        Font menuFont = new Font("arial", Font.BOLD, 60);
        g.setFont(menuFont);
        String str = "VICTORY!";
        g.drawString(str, winWidth/2 - str.length()*g.getFont().getSize()/4, winHeight/3 - g.getFont().getSize());
        g.setFont(new Font("arial", Font.BOLD, 12));
        str = (Game.GAME.victoriesUntilGameOver - Game.GAME.totalVictories) + " more to go!";
        g.drawString(str, winWidth/2 - str.length()*g.getFont().getSize()/4, winHeight/3 + g.getFont().getSize());
        g.setColor(Color.WHITE);
        
        menuFont = new Font("arial", Font.BOLD, 10);
        g.setFont(menuFont);
        for(int i = 0; i < buttons.length; i++) {
            g.setColor(buttonIndex ==i?Color.GREEN:Color.lightGray);
            g.fillRect(winWidth / 2, x + 153 +(buttonHeight + buttonBuffer)*i + g.getFont().getSize(), buttonWidth + 2, buttonHeight);
            g.setColor(Color.WHITE);
            g.drawString(buttons[i], winWidth / 2, y + 70 + (buttonHeight + buttonBuffer)*i + g.getFont().getSize() + 100);
        }  

        g.dispose();    
    }

    @Override
    public void update() {

    }

    @Override
    public Layer getLayer() {
        return layer;
    }
}
