package it.unicam.cs.mpgc.rpg.model;

import java.util.UUID;

/**
 * Rappresenta lo stato corrente della partita.
 * Tiene separata la progressione della partita dai dati del personaggio.
 */
public class GameState {

    private String saveId;
    private Player player;
    private int currentFloor;

    public GameState() {
    }

    public GameState(Player player) {
        this.saveId = UUID.randomUUID().toString();
        this.player = player;
        this.currentFloor = 1;
    }

    public String getSaveId() {
        return saveId;
    }

    public void setSaveId(String saveId) {
        this.saveId = saveId;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void nextFloor() {
        currentFloor++;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
