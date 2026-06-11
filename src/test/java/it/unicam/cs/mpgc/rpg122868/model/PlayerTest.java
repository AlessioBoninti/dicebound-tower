package it.unicam.cs.mpgc.rpg122868.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void newPlayerStartsWithInitialProgressionValues() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        assertEquals(1, player.getLevel());
        assertEquals(0, player.getExperience());
        assertEquals(0, player.getGold());
    }

    @Test
    void maxHpUsesVitalityFormulaAndCurrentHpStartsFull() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        assertEquals(40 + player.getStats().getVitality() * 5, player.getMaxHp());
        assertEquals(player.getMaxHp(), player.getCurrentHp());
    }

    @Test
    void takeDamageDoesNotGoBelowZero() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        player.takeDamage(player.getMaxHp() + 100);

        assertEquals(0, player.getCurrentHp());
    }

    @Test
    void healDoesNotExceedMaxHp() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);
        player.takeDamage(10);

        player.heal(1000);

        assertEquals(player.getMaxHp(), player.getCurrentHp());
    }

    @Test
    void addGoldIncreasesGold() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        player.addGold(25);

        assertEquals(25, player.getGold());
    }

    @Test
    void addExperienceAtLevelOneIncreasesLevel() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        player.addExperience(100);

        assertEquals(2, player.getLevel());
    }

    @Test
    void warriorLevelUpIncreasesStrength() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);
        int initialStrength = player.getStats().getStrength();

        player.addExperience(100);

        assertEquals(initialStrength + 1, player.getStats().getStrength());
    }

    @Test
    void mageLevelUpIncreasesIntelligence() {
        Player player = new Player("Hero", PlayerClass.MAGE);
        int initialIntelligence = player.getStats().getIntelligence();

        player.addExperience(100);

        assertEquals(initialIntelligence + 1, player.getStats().getIntelligence());
    }

    @Test
    void rogueLevelUpIncreasesDexterity() {
        Player player = new Player("Hero", PlayerClass.ROGUE);
        int initialDexterity = player.getStats().getDexterity();

        player.addExperience(100);

        assertEquals(initialDexterity + 1, player.getStats().getDexterity());
    }
}
