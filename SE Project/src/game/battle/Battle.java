package game.battle;

import engine.audio.MyAudioPlayer;
import engine.renderer.Animation;
import engine.utils.Main;
import game.main.Game;

import java.util.ArrayList;

public class Battle {

    public static class BattleCharacter {
        public Character character;
        public BattleCharacter target;
        public Action pendingAction;
        public boolean ally = false;

        int x = 0, y = 0;
        Animation animation;

        double healthRatio = 0;
        double staminaRatio = 0;
        double manaRatio = 0;

        BattleCharacter(Character character) {
            this.character = character;
            animation = new Animation(character.resource.IDLE_ANIMATION_PATH, 5);
            pendingAction = Action.REST;
        }
    }

    private static Battle BATTLE;

    // view
    private BattleView view;

    boolean allyTurn = true;
    int currentCharacter = 0;
    ArrayList<BattleCharacter> teamAlly = new ArrayList<>();
    ArrayList<BattleCharacter> teamEnemy = new ArrayList<>();

    Battle(BattleView view) {
        BATTLE = this;
        view.setBattle(this);
        this.view = view;
        teamAlly.add(new BattleCharacter(Game.GAME.playerCharacter));
        teamEnemy.add(new BattleCharacter(Character.GEN_BASIC_ENEMY("Enemy 0")));
        teamEnemy.add(new BattleCharacter(Character.GEN_BASIC_ENEMY("Enemy 1")));
        //teamEnemy.add(new BattleCharacter(Character.GEN_BASIC_ENEMY("Enemy 2")));
        for(var c : teamAlly) {
            c.ally = true;
        }
        view.updateAllBattleCharacters();
        view.processAllBattleCharacterViewPos();
    }

    int getWinningTeam() {
        if(teamAlly.size() == 0) {
            return 1;
        } else if(teamEnemy.size() == 0) {
            return 0;
        }
        return -1;
    }

    void processCurrentCharacter() {
        BattleCharacter bc = getCurrentBattleCharacter();
        if(bc.character.ai != null) {
            bc.pendingAction = bc.character.ai.getAction(bc);
        } else {
            BattleMenuLayer.setActive(true);
            while(BattleMenuLayer.active) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void changeTurn() {
        ArrayList<BattleCharacter> toRemove = new ArrayList<>();
        for(var c : getCurrentTeam()) {
            processAction(c);
            if(c.character.currentHealth <= 0) {
                toRemove.add(c);
            }
        }
        for(var c : toRemove) {
            getCurrentTeam().remove(c);
        }
        view.processAllBattleCharacterViewPos();
        allyTurn = !allyTurn;
        System.out.println("NEXT TURN");
    }

    public void nextCharacter() {
        currentCharacter++;
        if(currentCharacter == getCurrentTeam().size()) {
            currentCharacter = 0;
            changeTurn();
        }
        else System.out.println("NEXT CHARACTER");
    }

    public void previousCharacter() {
        getCurrentBattleCharacter().pendingAction = Action.REST;
        currentCharacter--;
    }

    public void processAction(BattleCharacter bc) {
        Action action = bc.pendingAction;
        if(bc.target == null) bc.target = getRandomTargetFrom(getTeam(!bc.ally));
        if(action.TARGET_EFFECT != null) {
            var reqs = action.ACTOR_EFFECT.getRequirement();
            if(reqs.staminaRequirement > 0) {
                bc.animation.setTempOverridingAnimation(new Animation(bc.character.resource.PHYSICAL_ATTACK_ANIMATION_PATH));
                MyAudioPlayer.play("res/audio/"+bc.pendingAction.AUDIO_PATH+".wav");
            } else if(reqs.manaRequirement > 0) {
                bc.animation.setTempOverridingAnimation(new Animation(bc.character.resource.MAGICAL_ATTACK_ANIMATION_PATH, 3));
                MyAudioPlayer.play("res/audio/"+bc.pendingAction.AUDIO_PATH+".wav");
            }
            while(bc.animation.hasTempOverridingAnimation()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            action.TARGET_EFFECT.effect(bc.character, bc.target.character);
        }
        if(action.ACTOR_EFFECT != null) {
            assert bc.target != null;
            action.ACTOR_EFFECT.effect(bc.target.character, bc.character);
        }
        System.out.println(bc.character.name + " : " + action.NAME +" -> "+ bc.target.character.name);
        bc.pendingAction = Action.REST;
        bc.target = null;
        view.updateAllBattleCharacters();
    }

    public Action getPendingAction() {
        return getCurrentBattleCharacter().pendingAction;
    }

    public BattleCharacter getCurrentBattleCharacter() {
        return (allyTurn ? teamAlly.get(currentCharacter) : teamEnemy.get(currentCharacter));
    }

    public static ArrayList<BattleCharacter> getCurrentTeam() {
        return (BATTLE.allyTurn ? BATTLE.teamAlly : BATTLE.teamEnemy);
    }

    public static ArrayList<BattleCharacter> getTeam(boolean ally) {
        return (ally ? BATTLE.teamAlly : BATTLE.teamEnemy);
    }

    public int getCurrentCharacter() {
        return currentCharacter;
    }

    public static BattleCharacter getRandomTarget(Battle.BattleCharacter battleCharacter) {
        BattleCharacter target;
        do {
            ArrayList<BattleCharacter> team = (Main.RAND.nextBoolean() ? BATTLE.teamAlly : BATTLE.teamEnemy);
            int pos = Main.RAND.nextInt(team.size());
            target = team.get(pos);
        } while(target == battleCharacter);
        return target;
    }

    public static BattleCharacter getRandomTargetFrom(ArrayList<BattleCharacter> team) {
        return team.get(Main.RAND.nextInt(team.size()));
    }

    public void setPendingAction(Action action) {
        getCurrentBattleCharacter().pendingAction = action;
    }
}
