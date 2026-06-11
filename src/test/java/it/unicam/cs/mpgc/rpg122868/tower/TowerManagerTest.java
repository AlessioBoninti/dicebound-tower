package it.unicam.cs.mpgc.rpg122868.tower;

import it.unicam.cs.mpgc.rpg122868.model.GameState;
import it.unicam.cs.mpgc.rpg122868.model.Player;
import it.unicam.cs.mpgc.rpg122868.model.PlayerClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TowerManagerTest {

    private final TowerManager towerManager = new TowerManager();

    @Test
    void floorOneIsBasicEnemy() {
        GameState gameState = new GameState(new Player("Hero", PlayerClass.WARRIOR));

        assertEquals(FloorType.BASIC_ENEMY, towerManager.getFloorType(gameState));
    }

    @Test
    void floorFiveIsCheckpoint() {
        GameState gameState = new GameState(new Player("Hero", PlayerClass.WARRIOR));
        gameState.setCurrentFloor(5);

        assertEquals(FloorType.CHECKPOINT, towerManager.getFloorType(gameState));
    }

    @Test
    void floorTenIsBoss() {
        GameState gameState = new GameState(new Player("Hero", PlayerClass.WARRIOR));
        gameState.setCurrentFloor(10);

        assertEquals(FloorType.BOSS, towerManager.getFloorType(gameState));
    }

    @Test
    void floorElevenIsCompleted() {
        GameState gameState = new GameState(new Player("Hero", PlayerClass.WARRIOR));
        gameState.setCurrentFloor(11);

        assertEquals(FloorType.COMPLETED, towerManager.getFloorType(gameState));
    }

    @Test
    void advanceFloorIncrementsFloor() {
        GameState gameState = new GameState(new Player("Hero", PlayerClass.WARRIOR));

        towerManager.advanceFloor(gameState);

        assertEquals(2, gameState.getCurrentFloor());
    }

    @Test
    void applyCheckpointHealsPlayerAndAdvancesFloor() {
        Player player = new Player("Hero", PlayerClass.WARRIOR);
        GameState gameState = new GameState(player);
        gameState.setCurrentFloor(5);
        player.takeDamage(30);

        towerManager.applyCheckpoint(gameState);

        assertEquals(6, gameState.getCurrentFloor());
        assertTrue(player.getCurrentHp() > player.getMaxHp() - 30);
    }
}
