package it.unicam.cs.mpgc.rpg122868.model;

/**
 * Rappresenta le statistiche principali di un personaggio.
 * Le statistiche influenzano HP, combattimento e progressione.
 */
public class Stats {

    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;

    public Stats() {
    }

    public Stats(int strength, int dexterity, int intelligence, int vitality) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getVitality() {
        return vitality;
    }

    public void increaseStrength() {
        strength++;
    }

    public void increaseDexterity() {
        dexterity++;
    }

    public void increaseIntelligence() {
        intelligence++;
    }

    public void increaseVitality() {
        vitality++;
    }
}
