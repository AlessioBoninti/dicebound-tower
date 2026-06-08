package it.unicam.cs.mpgc.rpg122868.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Rappresenta il personaggio controllato dal giocatore.
 * Contiene solo informazioni legate al personaggio, come nome,
 * classe, statistiche, livello, esperienza, oro e HP.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    private String name;
    private PlayerClass playerClass;
    private Stats stats;
    private int level;
    private int experience;
    private int gold;
    private int currentHp;
    private Inventory inventory;

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
        this.stats = playerClass.createInitialStats();
        this.currentHp = getMaxHp();
        this.inventory = new Inventory();
        this.inventory.addItem(new Item("Pozione di Cura Minore", ItemType.HEALING_POTION, 20));
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

    @JsonIgnore
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
        playerClass.increaseMainStat(stats);
    }

    /**
     * Calcola gli HP massimi usando la formula base del regolamento:
     * 40 + vitalità * 5.
     */
    @JsonIgnore
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

    public Inventory getInventory() {
        return inventory;
    }
}
