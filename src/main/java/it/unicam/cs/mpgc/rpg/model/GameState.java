package it.unicam.cs.mpgc.rpg.model;

/**
 * Rappresenta lo stato corrente della partita.
 * Tiene separata la progressione della partita dai dati del personaggio.
 */
public class GameState {

    private Player player;
    private int currentFloor;

    public GameState() {
    }

    public GameState(Player player) {
        this.player = player;
        this.currentFloor = 1;
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
