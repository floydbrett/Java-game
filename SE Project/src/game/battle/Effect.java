package game.battle;

import engine.utils.Main;

public interface Effect {

    class EffectRequirement {
        public int healthRequirement = 0;
        public int staminaRequirement = 0;
        public int manaRequirement = 0;

        public EffectRequirement(int healthRequirement, int staminaRequirement, int manaRequirement) {
            this.healthRequirement += healthRequirement;
            this.staminaRequirement += staminaRequirement;
            this.manaRequirement += manaRequirement;
        }

        public EffectRequirement(EffectRequirement ef, int healthRequirement, int staminaRequirement, int manaRequirement) {
            this(healthRequirement, staminaRequirement, manaRequirement);
            this.healthRequirement += ef.healthRequirement;
            this.staminaRequirement += ef.staminaRequirement;
            this.manaRequirement += ef.manaRequirement;
        }
    }

    void effect(Character actor, Character target);
    EffectRequirement getRequirement();

    static Effect HEALTH_DRAIN(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                //System.out.println("HEALTH_DRAIN("+amount+") -> " + target.name);
                target.currentHealth -= amount;
                if(target.currentHealth < 0) target.currentHealth = 0;
                if(target.currentHealth > target.maxHealth) target.currentHealth = target.maxHealth;
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, 0);
            }
        };
    }

    static Effect STAMINA_DRAIN(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                //System.out.println("STAMINA_DRAIN("+amount+") -> " + target.name);
                target.currentStamina -= amount;
                if(target.currentStamina < 0) target.currentStamina = 0;
                if(target.currentStamina > target.maxStamina) target.currentStamina = target.maxStamina;
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, 0);
            }
        };
    }

    static Effect MANA_DRAIN(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                //System.out.println("MANA_DRAIN("+amount+") -> " + target.name);
                target.currentMana -= amount;
                if(target.currentMana < 0) target.currentMana = 0;
                if(target.currentMana > target.maxMana) target.currentMana = target.maxMana;
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, 0);
            }
        };
    }

    static Effect HEALTH_SPEND(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                HEALTH_DRAIN(amount).effect(actor, target);
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(amount, 0, 0);
            }
        };
    }

    static Effect STAMINA_SPEND(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                STAMINA_DRAIN(amount).effect(actor, target);
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, amount, 0);
            }
        };
    }

    static Effect MANA_SPEND(int amount) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                MANA_DRAIN(amount).effect(actor, target);
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, amount);
            }
        };
    }

    static Effect PHYSICAL_ATTACK(int power) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                HEALTH_DRAIN(actor.attack * power).effect(actor, target);
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, 0);
            }
        };
    }

    static Effect MAGICAL_ATTACK(int power) {
        return new Effect() {
            @Override
            public void effect(Character actor, Character target) {
                HEALTH_DRAIN((int)(power * (1 - 0.2 * Main.RAND.nextDouble()))).effect(actor, target);
            }

            @Override
            public EffectRequirement getRequirement() {
                return new EffectRequirement(0, 0, 0);
            }
        };
    }
}
