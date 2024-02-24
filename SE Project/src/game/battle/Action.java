package game.battle;

public enum Action {
    REST("Rest", "RestSound", Effect.STAMINA_DRAIN(-4), null, true),
    STRIKE("Strike", "Quick_Strike", Effect.STAMINA_SPEND(2), Effect.PHYSICAL_ATTACK(3)),
    HEAVY_STRIKE("Heavy Strike", "HeavyStrike", Effect.STAMINA_SPEND(4), Effect.PHYSICAL_ATTACK(7)),
    FIREBALL("Fireball", null, Effect.MANA_SPEND(2), Effect.MAGICAL_ATTACK(15)),
    ;
    public final String NAME;
    public final String AUDIO_PATH;
    public final boolean BENEFICIAL;
    public final Effect ACTOR_EFFECT;
    public final Effect TARGET_EFFECT;

    Action(String name, String audioPath, Effect actorEffect, Effect targetEffect) {
        this.NAME = name;
        this.AUDIO_PATH = audioPath;
        this.ACTOR_EFFECT = actorEffect;
        this.TARGET_EFFECT = targetEffect;
        this.BENEFICIAL = false;
    }

    Action(String name, String audioPath, Effect actorEffect, Effect targetEffect, boolean beneficial) {
        this.NAME = name;
        this.AUDIO_PATH = audioPath;
        this.ACTOR_EFFECT = actorEffect;
        this.TARGET_EFFECT = targetEffect;
        this.BENEFICIAL = beneficial;
    }
}
