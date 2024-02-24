package engine.menus;

import engine.layers.Layer;
import engine.layers.LayerImplement;
import engine.input.MyKeyListener;
import engine.window.MyWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class MenuLayer implements LayerImplement {

    private final Layer layer = new Layer(this, this, this);
    protected Menu menu = new Menu();

    protected MenuLayer() {
        layer.setBlocks(false, true, true);
    }

    @Override
    public void render(BufferStrategy bs) {
        menu.render(bs);
    }

    @Override
    public void input(MyKeyListener.Input input) {
        menu.input(input);
    }

    @Override
    public void update() {

    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
