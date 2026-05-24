package it.unicam.cs.mpgc.rpg.combat;

import java.util.Random;

/**
 * Gestisce i tiri di dado usati nel combattimento.
 */
public class DiceRoller {

    private final Random random;

    public DiceRoller() {
        this.random = new Random();
    }

    /**
     * Tira un dado con il numero di facce indicato.
     */
    public int roll(int sides) {
        return random.nextInt(sides) + 1;
    }

    public int rollD20() {
        return roll(20);
    }

    public int rollDamageDie(int sides) {
        return roll(sides);
    }
}
