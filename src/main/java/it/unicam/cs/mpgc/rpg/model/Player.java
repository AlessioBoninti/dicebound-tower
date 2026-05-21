package it.unicam.cs.mpgc.rpg.model;


/**
 * Rappresenta il personaggio controllato dal giocatore.
 * Contiene solo informazioni legate al personaggio, come nome,
 * classe, statistiche, livello, esperienza, oro e HP.
 */
public class Player {

    private String name;
    private PlayerClass playerClass;
    private Stats stats;
    private int level;
    private int experience;
    private int gold;
    private int currentHp;

    /**
     * Costruttore vuoto usato dalla libreria di persistenza JSON
     * per ricostruire l'oggetto durante il caricamento.
     */
    public Player() {
    }


    /**
     * Crea un nuovo giocatore con statistiche iniziali
     * dipendenti dalla classe scelta.
     */
    public Player(String name, PlayerClass playerClass) {
        this.name = name;
        this.playerClass = playerClass;
        this.level = 1;
        this.experience = 0;
        this.gold = 0;
        this.stats = createInitialStats(playerClass);
        this.currentHp = getMaxHp();
    }

    /**
     * Restituisce le statistiche iniziali in base alla classe scelta.
     */
    private Stats createInitialStats(PlayerClass playerClass) {
        if (playerClass == PlayerClass.WARRIOR) {
            return new Stats(8, 4, 2, 8);
        }
        if (playerClass == PlayerClass.MAGE) {
            return new Stats(2, 4, 9, 5);
        }
        return new Stats(5, 9, 3, 4);
    }

    /**
     * Riduce gli HP attuali senza permettere che scendano sotto zero.
     */
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp < 0) {
            currentHp = 0;
        }
    }


    /**
     * Cura il giocatore senza superare gli HP massimi.
     */
    public void heal(int amount) {
        currentHp += amount;
        if (currentHp > getMaxHp()) {
            currentHp = getMaxHp();
        }
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    /**
     * Aggiunge esperienza al giocatore e gestisce eventuali level up.
     */
    public void addExperience(int amount) {
        experience += amount;

        while (experience >= level * 100) {
            experience -= level * 100;
            level++;
            increaseMainStat();
            currentHp = getMaxHp();
        }
    }

    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Aumenta la statistica principale in base alla classe del giocatore.
     */
    private void increaseMainStat() {
        if (playerClass == PlayerClass.WARRIOR) {
            stats.increaseStrength();
        } else if (playerClass == PlayerClass.MAGE) {
            stats.increaseIntelligence();
        } else if (playerClass == PlayerClass.ROGUE) {
            stats.increaseDexterity();
        }
    }

    /**
     * Calcola gli HP massimi usando la formula base del regolamento:
     * 40 + vitalità * 5.
     */
    public int getMaxHp() {
        return 40 + stats.getVitality() * 5;
    }

    public String getName() {
        return name;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public Stats getStats() {
        return stats;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getGold() {
        return gold;
    }

    public int getCurrentHp() {
        return currentHp;
    }
}
