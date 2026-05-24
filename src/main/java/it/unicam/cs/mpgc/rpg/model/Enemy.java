package it.unicam.cs.mpgc.rpg.model;

/**
 * Rappresenta un nemico che puo combattere contro il giocatore.
 */
public class Enemy {

    private String name;
    private int maxHp;
    private int currentHp;
    private int defense;
    private int attackBonus;
    private int damageDie;
    private int damageBonus;
    private int experienceReward;
    private int goldReward;


    /**
     * Costruttore vuoto usato dalla libreria di persistenza JSON
     * per ricostruire l'oggetto durante il caricamento.
     */
    public Enemy() {
    }

    public Enemy(String name, int maxHp, int defense, int attackBonus,
                 int damageDie, int damageBonus, int experienceReward, int goldReward) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.defense = defense;
        this.attackBonus = attackBonus;
        this.damageDie = damageDie;
        this.damageBonus = damageBonus;
        this.experienceReward = experienceReward;
        this.goldReward = goldReward;
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

    public boolean isAlive() {
        return currentHp > 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public int getDamageDie() {
        return damageDie;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getGoldReward() {
        return goldReward;
    }
}
