package it.unicam.cs.mpgc.rpg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce gli oggetti posseduti dal giocatore.
 */
public class Inventory {

    private List<Item> items;

    /**
     * Crea un inventario vuoto.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Aggiunge un oggetto all'inventario.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Rimuove un oggetto dall'inventario.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Restituisce gli oggetti presenti nell'inventario.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Indica se l'inventario contiene almeno una pozione di cura.
     */
    public boolean hasHealingPotion() {
        for (Item item : items) {
            if (item.getType() == ItemType.HEALING_POTION) {
                return true;
            }
        }
        return false;
    }

    /**
     * Usa la prima pozione di cura disponibile sul giocatore.
     */
    public boolean useHealingPotion(Player player) {
        for (Item item : items) {
            if (item.getType() == ItemType.HEALING_POTION) {
                player.heal(item.getValue());
                items.remove(item);
                return true;
            }
        }
        return false;
    }
}
