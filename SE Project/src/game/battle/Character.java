package game.battle;

import game.ai.BattleCharacterController;

import java.util.ArrayList;
import java.util.List;

public class Character {
    enum ResourcePath {
        DEFAULT("man0"),
        MAN_0("man0"),
        ;
        final String PATH;
        final String IDLE_ANIMATION_PATH;
        final String MAGICAL_ATTACK_ANIMATION_PATH;
        final String PHYSICAL_ATTACK_ANIMATION_PATH;

        ResourcePath(String path) {
            PATH = path;
            IDLE_ANIMATION_PATH = PATH + "/idle";
            MAGICAL_ATTACK_ANIMATION_PATH = PATH + "/magical_attack";
            PHYSICAL_ATTACK_ANIMATION_PATH = PATH + "/physical_attack";
        }
    }

    public ArrayList<Action> magicArray = new ArrayList<>();
    public ArrayList<Action> abilitiesArray = new ArrayList<>();
    public ArrayList<Action> itemsArray = new ArrayList<>();

    public ResourcePath resource = ResourcePath.DEFAULT;

    public String name;
    public int currentHealth, maxHealth;
    public int currentStamina, maxStamina;
    public int currentMana, maxMana;
    public int attack = 1, defense = 1;
    public BattleCharacterController ai = null;

    public Character(String name) {
        this.name = name;
    }

    public Character(String name, int maxHealth, int maxStamina, int maxMana) {
        this(name);
        this.maxHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.maxMana = maxMana;
        currentHealth = maxHealth;
        currentStamina = maxStamina;
        currentMana = maxMana;
    }

    public ArrayList<Action> getValidMagicActions() {
        ArrayList<Action> out = new ArrayList<>();
        for(var s : magicArray) {
            if(meetsRequirements(s)) {
                out.add(s);
            }
        }
        return out;
    }

    public ArrayList<Action> getValidAbilityActions() {
        ArrayList<Action> out = new ArrayList<>();
        for(var s : abilitiesArray) {
            if(meetsRequirements(s)) {
                out.add(s);
            }
        }
        return out;
    }

    public ArrayList<Action> getValidItemActions() {
        ArrayList<Action> out = new ArrayList<>();
        for(var s : itemsArray) {
            if(meetsRequirements(s)) {
                out.add(s);
            }
        }
        return out;
    }

    public ArrayList<Action> getAllValidActions() {
        ArrayList<Action> out = new ArrayList<>();
        out.addAll(getValidMagicActions());
        out.addAll(getValidAbilityActions());
        out.addAll(getValidItemActions());
        return out;
    }

    public boolean meetsRequirements(Action action) {
        Effect.EffectRequirement actorReqs = (action.ACTOR_EFFECT != null) ? action.ACTOR_EFFECT.getRequirement() : null;
        Effect.EffectRequirement targetReqs = (action.TARGET_EFFECT != null) ? action.TARGET_EFFECT.getRequirement() : null;
        int healthReq =
                ((actorReqs != null) ? actorReqs.healthRequirement : 0) +
                        ((targetReqs != null) ? targetReqs.healthRequirement : 0);
        int staminaReq =
                ((actorReqs != null) ? actorReqs.staminaRequirement : 0) +
                        ((targetReqs != null) ? targetReqs.staminaRequirement : 0);
        int manaReq =
                ((actorReqs != null) ? actorReqs.manaRequirement : 0) +
                        ((targetReqs != null) ? targetReqs.manaRequirement : 0);
        if (
                this.currentHealth < healthReq ||
                        this.currentStamina < staminaReq ||
                        this.currentMana < manaReq
        ) {
            return false;
        }
        return true;
    }

    public void setResource(ResourcePath resource) {
        this.resource = resource;
    }

    public static Character GEN_BASIC_ENEMY(String name) {
        Character out = new Character(name, 10, 4, 0);
        Action[] a = {Action.REST, Action.STRIKE};
        out.abilitiesArray = new ArrayList<>(List.of(a));
        out.ai = BattleCharacterController.AI_RANDOM();
        out.setResource(ResourcePath.MAN_0);
        return out;
    }

    public static Character GEN_BASIC_ENEMY() {
        return GEN_BASIC_ENEMY("Enemy");
    }
}
