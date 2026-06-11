package it.unicam.cs.mpgc.rpg122868.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class PlayerClassTest {

    @Test
    void warriorCreatesExpectedInitialStats() {
        Stats stats = PlayerClass.WARRIOR.createInitialStats();

        assertEquals(8, stats.getStrength());
        assertEquals(4, stats.getDexterity());
        assertEquals(2, stats.getIntelligence());
        assertEquals(8, stats.getVitality());
    }

    @Test
    void mageCreatesExpectedInitialStats() {
        Stats stats = PlayerClass.MAGE.createInitialStats();

        assertEquals(2, stats.getStrength());
        assertEquals(4, stats.getDexterity());
        assertEquals(9, stats.getIntelligence());
        assertEquals(5, stats.getVitality());
    }

    @Test
    void rogueCreatesExpectedInitialStats() {
        Stats stats = PlayerClass.ROGUE.createInitialStats();

        assertEquals(5, stats.getStrength());
        assertEquals(9, stats.getDexterity());
        assertEquals(3, stats.getIntelligence());
        assertEquals(4, stats.getVitality());
    }

    @Test
    void createInitialStatsReturnsNewInstance() {
        Stats first = PlayerClass.WARRIOR.createInitialStats();
        Stats second = PlayerClass.WARRIOR.createInitialStats();

        assertNotSame(first, second);
    }

    @Test
    void getMainStatValueReturnsExpectedStat() {
        Stats stats = new Stats(8, 4, 9, 5);

        assertEquals(8, PlayerClass.WARRIOR.getMainStatValue(stats));
        assertEquals(9, PlayerClass.MAGE.getMainStatValue(stats));
        assertEquals(4, PlayerClass.ROGUE.getMainStatValue(stats));
    }
}
