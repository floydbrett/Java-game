package game.main;

import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerController;
import engine.layers.LayerImplement;
import engine.utils.MainMenuLayer;
import engine.window.MyWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameVictoryLayer implements LayerImplement {
    private final Layer layer = new Layer(this, this, this);

    @Override
    public void input(MyKeyListener.Input input) {
        LayerController.clearLayers();
        LayerController.pushLayer(new MainMenuLayer().getLayer());
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public void render(BufferStrategy bs) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        String str = "YOU BEAT THE GAME!";
        g.drawString(str, MyWindow.getCanvas().getWidth()/2 - str.length()*g.getFont().getSize()/4, MyWindow.getCanvas().getHeight()/2 - g.getFont().getSize());
        g.dispose();
    }

    @Override
    public void update() {

    }
}
