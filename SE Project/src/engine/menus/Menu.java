package engine.menus;

import engine.input.InputHandler;
import engine.input.MyKeyListener;
import engine.renderer.Renderable;
import engine.window.MyWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Renderable, InputHandler {

    protected ArrayList<MenuButton> buttons;
    protected int buttonIndex = 0;
    protected int buttonWidth = 63, buttonHeight = 20, buttonBuffer = 5;
    protected int x, y;
    protected Color backdropColor = Color.black,
            buttonBackdropColor = Color.black,
            selectedBackdropColor = buttonBackdropColor,
            outlineColor = Color.white,
            selectedOutlineColor = Color.green,
            textColor = Color.black;
    protected boolean vertical = true;
    protected int buttonWindow = 4;

    public void createButtons(MenuButton[] buttons) {
        this.buttons = new ArrayList<>(List.of(buttons));
    }

    public void createButtons(ArrayList<MenuButton> buttons) {
        this.buttons = new ArrayList<>(buttons);
    }

    public void centerMenu() {
        x = (MyWindow.getCanvas().getWidth() - buttonWidth)/2;
        y = (MyWindow.getCanvas().getHeight() - (buttonHeight + buttonBuffer)*this.buttons.size())/2;
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case DOWN:
            case RIGHT:
                buttonIndex = (buttonIndex +1)%buttons.size();
                break;
            case UP:
            case LEFT:
                buttonIndex = buttonIndex ==0? buttons.size()-1: buttonIndex -1;
                break;
            case A:
            case SELECT:
                buttons.get(buttonIndex).onPress();
                break;
        }
    }

    @Override
    public void render(BufferStrategy bs) {
        Graphics2D g1 = (Graphics2D) bs.getDrawGraphics();
        g1.setColor(Color.white);
        Font titleFont = new Font("arial", Font.BOLD, 40);
        g1.setFont(titleFont);
        g1.drawString("RPG Game", (MyWindow.getCanvas().getWidth() / 2) - 100, y - 80);
        for(int i = 0; i < buttons.size(); i++) {
            int bx = vertical ? x : x + i * (buttonWidth + buttonBuffer);
            int by = !vertical ? y : y + i * (buttonHeight + buttonBuffer);
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(buttonIndex == i ? selectedBackdropColor : buttonBackdropColor);
            g.fillRect(bx, by, buttonWidth, buttonHeight);
            g.setColor(buttonIndex == i ? selectedOutlineColor : outlineColor);
            g.fillRect(bx, by, buttonWidth, buttonHeight);
            g.dispose();
            buttons.get(i).renderText(
                    bs,
                    textColor,
                    bx,
                    by + g.getFont().getSize(),
                    buttonWidth,
                    buttonHeight
            );
        }
        bs.show();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void center() {
        setPosition(MyWindow.getCanvas().getWidth()/2-buttonWidth/2, MyWindow.getCanvas().getHeight()/2-(buttonHeight+buttonBuffer)*buttons.size()/2);
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTotalWidth() {
        return vertical ? buttonWidth : buttons.size() * (buttonWidth + buttonBuffer);
    }

    public int getTotalHeight() {
        return !vertical ? buttonHeight : buttons.size() * (buttonHeight + buttonBuffer);
    }
}
