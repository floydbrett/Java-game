package game.main;

import game.battle.Action;
import game.battle.Character;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static Game GAME = new Game();
    public int totalVictories = 0, victoriesUntilGameOver = 3;
    public Character playerCharacter = genMainCharacter();

    private static Character genMainCharacter() {
        Character out = new Character("Player", 50, 8, 5);
        Action[] a = {Action.REST, Action.STRIKE, Action.HEAVY_STRIKE};
        out.abilitiesArray = new ArrayList<>(List.of(a));
        a = new Action[]{Action.FIREBALL};
        out.magicArray = new ArrayList<>(List.of(a));
        return out;
    }
}
