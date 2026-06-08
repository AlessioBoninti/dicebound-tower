package it.unicam.cs.mpgc.rpg122868.tower;

import it.unicam.cs.mpgc.rpg122868.model.GameState;
import it.unicam.cs.mpgc.rpg122868.model.Player;

/**
 * Gestisce in modo semplice la progressione nella Torre delle Rovine.
 */
public class TowerManager {

    private static final int FINAL_FLOOR = 10;
    private static final int CHECKPOINT_FLOOR = 5;

    /**
     * Restituisce il tipo del piano corrente.
     */
    public FloorType getFloorType(GameState gameState) {
        int currentFloor = gameState.getCurrentFloor();

        if (currentFloor > FINAL_FLOOR) {
            return FloorType.COMPLETED;
        }
        if (currentFloor == FINAL_FLOOR) {
            return FloorType.BOSS;
        }
        if (currentFloor == CHECKPOINT_FLOOR) {
            return FloorType.CHECKPOINT;
        }
        return FloorType.BASIC_ENEMY;
    }

    /**
     * Controlla se la torre e stata completata.
     */
    public boolean isTowerCompleted(GameState gameState) {
        return gameState.getCurrentFloor() > FINAL_FLOOR;
    }

    /**
     * Avanza al piano successivo.
     */
    public void advanceFloor(GameState gameState) {
        gameState.nextFloor();
    }

    /**
     * Applica il checkpoint curando il giocatore secondo il regolamento e
     * avanzando di piano.
     */
    public void applyCheckpoint(GameState gameState) {
        Player player = gameState.getPlayer();
        player.heal(player.getMaxHp() / 2);
        advanceFloor(gameState);
    }
}
