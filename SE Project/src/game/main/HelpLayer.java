package game.main;

import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerController;
import engine.layers.LayerImplement;
import engine.window.MyWindow;
import game.Help.HelperLayer;
import engine.utils.MainMenuLayer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class HelpLayer implements LayerImplement {
    private final Layer layer = new Layer(this, this, this);

    protected String[] buttons;
    protected int buttonIndex = 0;
    protected int buttonWidth = 50, buttonHeight = 20, buttonBuffer = 5;
    protected int x;
    protected int y;

    public HelpLayer() {
        getLayer().setName("Help");
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case START:
                LayerController.clearLayers();
                LayerController.pushLayer((new MainMenuLayer()).getLayer());
                break;
        }
    }

    @Override
    public void render(BufferStrategy bs) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(new Color(50, 100, 50));
        g.fillRect(0, 0, MyWindow.getCanvas().getWidth(), MyWindow.getCanvas().getHeight());
        g.dispose();
    }

    @Override
    public void update() {
        LayerController.pushLayer(new HelperLayer().getLayer());
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    protected void createButtons(String[] buttons) {
        this.buttons = buttons;
        x = (MyWindow.getCanvas().getWidth() - buttonWidth)/2;
        y = (MyWindow.getCanvas().getHeight() - (buttonHeight + buttonBuffer)*buttons.length)/2;
    }
}
