package game.battle;

import engine.audio.MyAudioPlayer;
import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerController;
import engine.layers.LayerImplement;
import game.main.Game;
import game.main.GameOverLayer;

import java.awt.image.BufferStrategy;

public class BattleLayer implements LayerImplement {
    private final Layer layer = new Layer(this, this, this);

    // view
    BattleView view = new BattleView();

    // battle data
    private final Battle battle = new Battle(view);

    // menu data
    private final BattleMenuLayer battleMenu = new BattleMenuLayer(battle);

    public BattleLayer() {
        layer.setName("Battle");
        layer.setBlocks(true, true, false);
    }

    private void processBattle() {
        BattleMenuLayer.setActive(false);
        while(battle.getWinningTeam() == -1) {
            battle.processCurrentCharacter();
            battle.nextCharacter();
        }
        if(battle.getWinningTeam() == 0) {
            // win
            System.out.println("YOU WIN");
            MyAudioPlayer.play("res/audio/NextLevel.wav");
            Game.GAME.totalVictories++;
            LayerController.popLayers(2);
            LayerController.pushLayer(new NextLevelLayer().getLayer());
        } else if(battle.getWinningTeam() == 1) {
            // lose
            System.out.println("YOU LOSE");
            MyAudioPlayer.play("res/audio/LoseSound.wav");
            LayerController.popLayers(2);
            LayerController.pushLayer(new GameOverLayer().getLayer());
        }
    }

    @Override
    public void input(MyKeyListener.Input input) {

    }

    @Override
    public void render(BufferStrategy bs) {
        view.render(bs);
    }

    @Override
    public void update() {
        LayerController.pushLayer(battleMenu.getLayer());
        processBattle();
    }

    @Override
    public Layer getLayer() {
        return layer;
    }
}
