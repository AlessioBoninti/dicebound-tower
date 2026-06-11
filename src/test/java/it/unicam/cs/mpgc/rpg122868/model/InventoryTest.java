package it.unicam.cs.mpgc.rpg122868.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryTest {

    @Test
    void newInventoryIsEmpty() {
        Inventory inventory = new Inventory();

        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    void addItemAddsItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("Potion", ItemType.HEALING_POTION, 20);

        inventory.addItem(item);

        assertEquals(1, inventory.getItems().size());
        assertTrue(inventory.getItems().contains(item));
    }

    @Test
    void removeItemRemovesItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("Potion", ItemType.HEALING_POTION, 20);
        inventory.addItem(item);

        inventory.removeItem(item);

        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    void hasHealingPotionReturnsTrueWhenPotionIsPresent() {
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Potion", ItemType.HEALING_POTION, 20));

        assertTrue(inventory.hasHealingPotion());
    }

    @Test
    void useHealingPotionHealsPlayerAndRemovesPotion() {
        Inventory inventory = new Inventory();
        Player player = new Player("Hero", PlayerClass.WARRIOR);
        player.takeDamage(30);
        inventory.addItem(new Item("Potion", ItemType.HEALING_POTION, 20));

        boolean used = inventory.useHealingPotion(player);

        assertTrue(used);
        assertEquals(player.getMaxHp() - 10, player.getCurrentHp());
        assertFalse(inventory.hasHealingPotion());
    }

    @Test
    void useHealingPotionReturnsFalseWhenNoPotionIsPresent() {
        Inventory inventory = new Inventory();
        Player player = new Player("Hero", PlayerClass.WARRIOR);

        assertFalse(inventory.useHealingPotion(player));
    }
}
