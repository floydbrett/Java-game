package engine.menus;

import game.battle.Action;

import java.awt.*;
import java.awt.image.BufferStrategy;

public interface MenuButton {
    void renderText(BufferStrategy bs, Color textColor, int x, int y, int w, int h);
    void onPress();

    abstract class TextButton implements MenuButton {
        public String text;

        public TextButton(String text) {
            this.text = text;
        }

        @Override
        public void renderText(BufferStrategy bs, Color textColor, int x, int y, int w, int h) {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(textColor);
            //g.setFont(font);
            g.drawString(text, x, y);
            g.dispose();
        }
    }
}
