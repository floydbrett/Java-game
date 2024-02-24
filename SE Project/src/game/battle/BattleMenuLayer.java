package game.battle;

import engine.input.MyKeyListener;
import engine.menus.Menu;
import engine.menus.MenuButton;
import engine.menus.MenuLayer;
import engine.layers.LayerController;
import engine.utils.MainMenuLayer;
import engine.audio.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class BattleMenuLayer extends MenuLayer {

    public static boolean active;
    private Battle battle;
    private int x = 10, y = 10;

    public BattleMenuLayer(Battle battle) {
        this.battle = battle;
        getLayer().setName("BattleMenu");
        getLayer().setBlocks(false, true, false);
        menu = genPrimaryMenu();
        active = true;
    }

    private Menu genPrimaryMenu() {
        Menu menu = new Menu();
        MenuButton finalButton = new MenuButton.TextButton("Flee") {
            @Override
            public void onPress() {
                LayerController.clearLayers();
                MainMenuLayer menu = new MainMenuLayer();
                LayerController.pushLayer(menu.getLayer());
            }
        };
        if(battle.getCurrentCharacter() > 0) {
            finalButton = new MenuButton.TextButton("Back") {
                @Override
                public void onPress() {
                    battle.previousCharacter();
                }
            };
        }
        menu.createButtons(new MenuButton[]{
                new MenuButton.TextButton("Ability") {
                    @Override
                    public void onPress() {
                        setMenu(genActionMenu(battle.getCurrentBattleCharacter().character.getValidAbilityActions()));
                    }
                },
                new MenuButton.TextButton("Magic") {
                    @Override
                    public void onPress() {
                        setMenu(genActionMenu(battle.getCurrentBattleCharacter().character.getValidMagicActions()));
                    }
                },
                new MenuButton.TextButton("Item") {
                    @Override
                    public void onPress() {
                        setMenu(genActionMenu(battle.getCurrentBattleCharacter().character.getValidItemActions()));
                    }
                },
                finalButton,
        });
        menu.setVertical(false);
        menu.setPosition(x, y);
        return menu;
    }

    private Menu genActionMenu(ArrayList<Action> actions) {
        ArrayList<MenuButton> menuButtons = new ArrayList<>();
        for(var ability : actions) {
            if(java.util.List.of(Action.values()).contains(ability)) {
                menuButtons.add(new MenuButton.TextButton(ability.NAME) {
                    @Override
                    public void onPress() {
                        battle.setPendingAction(ability);
                        setMenu(genTargetMenu((ability.BENEFICIAL) ? battle.teamAlly : battle.teamEnemy));
                    }
                });
            }
        }
        menuButtons.add(new MenuButton.TextButton("Back") {
            @Override
            public void onPress() {
                setMenu(genPrimaryMenu());
            }
        });

        Menu menu = new Menu();
        menu.createButtons(menuButtons);
        menu.setVertical(false);
        menu.setPosition(x, y);
        return menu;
    }

    private Menu genTargetMenu(ArrayList<Battle.BattleCharacter> battleCharacters) {
        ArrayList<MenuButton> menuButtons = new ArrayList<>();
        for(var bc : battleCharacters) {
            menuButtons.add(new MenuButton.TextButton(bc.character.name) {
                @Override
                public void onPress() {
                    battle.getCurrentBattleCharacter().target = bc;
                    setMenu(genPrimaryMenu());
                    setActive(false);
                }
            });
        }
        menuButtons.add(new MenuButton.TextButton("Back") {
            @Override
            public void onPress() {
                setMenu(genPrimaryMenu());
            }
        });

        Menu menu = new Menu();
        menu.createButtons(menuButtons);
        menu.setVertical(true);
        menu.setPosition(x, y);
        return menu;
    }

    @Override
    public void render(BufferStrategy bs) {
        menu.render(bs);
        if(!active) {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(menu.getX(), menu.getY(), menu.getTotalWidth(), menu.getTotalHeight());
            g.dispose();
        }
    }

    @Override
    public void input(MyKeyListener.Input input) {
        if(active) {
            super.input(input);
        }
    }

    public static void setActive(boolean active) {
        BattleMenuLayer.active = active;
    }
}
