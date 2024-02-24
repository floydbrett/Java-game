package engine.utils;

import engine.layers.LayerController;
import engine.renderer.Renderer;
import engine.updating.Updater;

import java.util.Random;

public class Main {

    public static final Random RAND = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Renderer renderer = new Renderer();
        renderer.getLatch().await();
        Updater updater = new Updater();
        updater.getLatch().await();
        LoopThread.startLoops();

        MainMenuLayer menu = new MainMenuLayer();
        LayerController.pushLayer(menu.getLayer());
    }
}
