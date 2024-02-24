package game.ai;

import game.battle.Action;
import game.battle.Battle;

import java.util.function.Function;

public abstract class StateMachineAI implements BattleCharacterController {

    static class State {
        String name;
        Function<Battle.BattleCharacter, Action> getAction;
        Function<Battle.BattleCharacter, State> getNextState;
    }

    protected State initState;
    protected State exitState;
    protected State currentState;

    public void setInitState(State initState) {
        this.initState = initState;
    }

    public void setExitState(State exitState) {
        this.exitState = exitState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
