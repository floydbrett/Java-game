package game.main;

import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerController;
import engine.layers.LayerImplement;
import engine.utils.MainMenuLayer;
import engine.window.MyWindow;
import game.battle.BattleLayer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameLayer implements LayerImplement {
    private final Layer layer = new Layer(this, this, this);

    public GameLayer() {
        getLayer().setName("Game");
        Game.GAME = new Game();
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case START:
                LayerController.pushLayer((new GameMenuLayer()).getLayer());
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
        if(Game.GAME.totalVictories < Game.GAME.victoriesUntilGameOver) {
            LayerController.pushLayer(new BattleLayer().getLayer());
        } else {
            LayerController.clearLayers();
            LayerController.pushLayer(new GameVictoryLayer().getLayer());
        }
    }

    @Override
    public Layer getLayer() {
        return layer;
    }
}
