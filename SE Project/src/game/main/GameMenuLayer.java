package game.main;

import engine.layers.LayerController;
import engine.menus.MenuButton;
import engine.menus.MenuLayer;
import engine.utils.MainMenuLayer;

public class GameMenuLayer extends MenuLayer {

    public GameMenuLayer() {
        getLayer().setName("GameMenu");
        menu.createButtons(new MenuButton[]{
                new MenuButton.TextButton("Continue") {
                    @Override
                    public void onPress() {
                        LayerController.popLayer();
                    }
                },
                new MenuButton.TextButton("Exit") {
                    @Override
                    public void onPress() {
                        LayerController.clearLayers();
                        LayerController.pushLayer(new MainMenuLayer().getLayer());
                    }
                },
        });
        menu.center();
    }
}
