package it.unicam.cs.mpgc.rpg122868.model;


/**
 * Rappresenta le classi giocabili disponibili nella prima versione del gioco.
 */
public enum PlayerClass {
    WARRIOR("Warrior", new Stats(8, 4, 2, 8), StatType.STRENGTH),
    MAGE("Mage", new Stats(2, 4, 9, 5), StatType.INTELLIGENCE),
    ROGUE("Rogue", new Stats(5, 9, 3, 4), StatType.DEXTERITY);

    private final String displayName;
    private final Stats initialStats;
    private final StatType mainStatType;

    PlayerClass(String displayName, Stats initialStats, StatType mainStatType) {
        this.displayName = displayName;
        this.initialStats = initialStats;
        this.mainStatType = mainStatType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Stats createInitialStats() {
        return new Stats(
                initialStats.getStrength(),
                initialStats.getDexterity(),
                initialStats.getIntelligence(),
                initialStats.getVitality()
        );
    }

    public StatType getMainStatType() {
        return mainStatType;
    }

    public int getMainStatValue(Stats stats) {
        return switch (mainStatType) {
            case STRENGTH -> stats.getStrength();
            case DEXTERITY -> stats.getDexterity();
            case INTELLIGENCE -> stats.getIntelligence();
            case VITALITY -> stats.getVitality();
        };
    }

    public void increaseMainStat(Stats stats) {
        switch (mainStatType) {
            case STRENGTH -> stats.increaseStrength();
            case DEXTERITY -> stats.increaseDexterity();
            case INTELLIGENCE -> stats.increaseIntelligence();
            case VITALITY -> stats.increaseVitality();
        }
    }
}
