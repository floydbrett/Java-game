package game.ai;

import engine.utils.Main;
import game.battle.Action;
import game.battle.Battle;
import game.battle.Character;

import java.util.Random;

public interface BattleCharacterController {
    Action getAction(Battle.BattleCharacter battleCharacter);

    static BattleCharacterController AI_RANDOM() {
        return battleCharacter -> {
            var validActions = battleCharacter.character.getAllValidActions();
            if(validActions.size() == 0)
                new Exception("No valid actions.").printStackTrace();
            return validActions.get(Main.RAND.nextInt(validActions.size()));
        };
    }
}
