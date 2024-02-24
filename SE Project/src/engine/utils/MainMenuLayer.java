package engine.utils;

import engine.layers.LayerController;
import engine.menus.MenuButton;
import engine.menus.MenuLayer;
import game.main.CharacterSelectionLayer;
import game.main.GameLayer;
import game.main.HelpLayer;
import engine.window.MyWindow;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferStrategy;

public class MainMenuLayer extends MenuLayer {

    public MainMenuLayer() {
        getLayer().setName("MainMenu");
        menu.createButtons(new MenuButton[]{
                new MenuButton.TextButton("Start") {
                    @Override
                    public void onPress() {
                        LayerController.popLayer();
                        LayerController.pushLayer(new GameLayer().getLayer());
                    }
                },
                new MenuButton.TextButton("Help") {
                    @Override
                    public void onPress() {
                        LayerController.popLayer();
                        LayerController.pushLayer(new HelpLayer().getLayer());
                    }
                },
                new MenuButton.TextButton("Characters") {
                    @Override
                    public void onPress() {
                        LayerController.popLayer();
                        LayerController.pushLayer(new CharacterSelectionLayer().getLayer());
                    }
                },
                new MenuButton.TextButton("Exit") {
                    @Override
                    public void onPress() {
                        MyWindow.close();
                    }
                },
        });
        menu.center();
    }
}
