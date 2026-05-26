package it.unicam.cs.mpgc.rpg.model;

/**
 * Rappresenta un oggetto utilizzabile o conservabile nell'inventario.
 */
public class Item {

    private String name;
    private ItemType type;
    private int value;

    /**
     * Costruttore vuoto usato dalla persistenza JSON.
     */
    public Item() {
    }

    /**
     * Crea un oggetto con nome, tipo e valore indicati.
     */
    public Item(String name, ItemType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
