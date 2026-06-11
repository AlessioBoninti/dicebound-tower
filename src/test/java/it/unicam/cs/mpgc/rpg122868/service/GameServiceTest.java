package it.unicam.cs.mpgc.rpg122868.service;

import it.unicam.cs.mpgc.rpg122868.model.GameState;
import it.unicam.cs.mpgc.rpg122868.model.PlayerClass;
import it.unicam.cs.mpgc.rpg122868.persistence.GameRepository;
import it.unicam.cs.mpgc.rpg122868.persistence.SaveInfo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {

    @Test
    void startNewGameCreatesGameStateAndPlayer() {
        GameService gameService = new GameService(new FakeGameRepository());

        gameService.startNewGame("Hero", PlayerClass.WARRIOR);

        assertNotNull(gameService.getGameState());
        assertNotNull(gameService.getPlayer());
        assertEquals("Hero", gameService.getPlayer().getName());
    }

    @Test
    void hasGameStartedChangesAfterStartNewGame() {
        GameService gameService = new GameService(new FakeGameRepository());

        assertFalse(gameService.hasGameStarted());

        gameService.startNewGame("Hero", PlayerClass.WARRIOR);

        assertTrue(gameService.hasGameStarted());
    }

    @Test
    void newGameStartsAtFloorOne() {
        GameService gameService = new GameService(new FakeGameRepository());

        gameService.startNewGame("Hero", PlayerClass.WARRIOR);

        assertEquals(1, gameService.getCurrentFloor());
    }

    @Test
    void saveGameStoresCurrentStateInRepository() {
        FakeGameRepository repository = new FakeGameRepository();
        GameService gameService = new GameService(repository);
        gameService.startNewGame("Hero", PlayerClass.WARRIOR);

        gameService.saveGame();

        assertSame(gameService.getGameState(), repository.savedGameState);
    }

    @Test
    void loadGameRestoresStateFromRepository() {
        FakeGameRepository repository = new FakeGameRepository();
        GameState savedState = new GameState(new it.unicam.cs.mpgc.rpg122868.model.Player("Loaded", PlayerClass.MAGE));
        repository.savedGameState = savedState;
        GameService gameService = new GameService(repository);

        gameService.loadGame();

        assertSame(savedState, gameService.getGameState());
        assertEquals("Loaded", gameService.getPlayer().getName());
    }

    @Test
    void useHealingPotionReturnsTrueWhenPlayerHasInitialPotion() {
        GameService gameService = new GameService(new FakeGameRepository());
        gameService.startNewGame("Hero", PlayerClass.WARRIOR);
        gameService.getPlayer().takeDamage(10);

        assertTrue(gameService.useHealingPotion());
    }

    @Test
    void completeCurrentFloorFromFloorOneAdvancesToFloorTwo() {
        GameService gameService = new GameService(new FakeGameRepository());
        gameService.startNewGame("Hero", PlayerClass.WARRIOR);

        gameService.completeCurrentFloor();

        assertEquals(2, gameService.getCurrentFloor());
    }

    private static class FakeGameRepository implements GameRepository {
        private GameState savedGameState;

        @Override
        public void save(GameState gameState) {
            this.savedGameState = gameState;
        }

        @Override
        public GameState load() {
            return savedGameState;
        }

        @Override
        public GameState load(String fileName) {
            return savedGameState;
        }

        @Override
        public List<SaveInfo> listSaves() {
            return Collections.emptyList();
        }

        @Override
        public boolean saveExists() {
            return savedGameState != null;
        }

        @Override
        public void deleteSave(String fileName) {
            savedGameState = null;
        }
    }
}
