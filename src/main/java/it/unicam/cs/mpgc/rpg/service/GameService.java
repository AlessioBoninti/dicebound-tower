package it.unicam.cs.mpgc.rpg.service;

import it.unicam.cs.mpgc.rpg.combat.CombatManager;
import it.unicam.cs.mpgc.rpg.combat.CombatResult;
import it.unicam.cs.mpgc.rpg.factory.EnemyFactory;
import it.unicam.cs.mpgc.rpg.factory.RuinsEnemyFactory;
import it.unicam.cs.mpgc.rpg.model.Enemy;
import it.unicam.cs.mpgc.rpg.model.GameState;
import it.unicam.cs.mpgc.rpg.model.Player;
import it.unicam.cs.mpgc.rpg.model.PlayerClass;
import it.unicam.cs.mpgc.rpg.tower.FloorType;
import it.unicam.cs.mpgc.rpg.tower.TowerManager;

/**
 * Coordina lo stato principale della partita e le operazioni di gioco.
 */
public class GameService {

    private GameState gameState;
    private Enemy currentEnemy;
    private final CombatManager combatManager;
    private final TowerManager towerManager;
    private final EnemyFactory enemyFactory;
    private boolean rewardsGiven;

    /**
     * Prepara i gestori usati durante la partita.
     */
    public GameService() {
        this.combatManager = new CombatManager();
        this.towerManager = new TowerManager();
        this.enemyFactory = new RuinsEnemyFactory();
        this.rewardsGiven = false;
    }

    /**
     * Avvia una nuova partita con il giocatore indicato.
     */
    public void startNewGame(String playerName, PlayerClass playerClass) {
        Player player = new Player(playerName, playerClass);
        this.gameState = new GameState(player);
        this.currentEnemy = null;
        this.rewardsGiven = false;
    }

    /**
     * Restituisce lo stato corrente della partita.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Restituisce il nemico attualmente in combattimento.
     */
    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    /**
     * Restituisce il tipo del piano corrente.
     */
    public FloorType getCurrentFloorType() {
        return towerManager.getFloorType(gameState);
    }

    /**
     * Prepara il nemico previsto dal piano corrente.
     */
    public Enemy prepareEnemyForCurrentFloor() {
        FloorType floorType = getCurrentFloorType();

        if (floorType == FloorType.BASIC_ENEMY) {
            currentEnemy = enemyFactory.createRandomBasicEnemy();
        } else if (floorType == FloorType.BOSS) {
            currentEnemy = enemyFactory.createBoss();
        } else {
            currentEnemy = null;
        }

        rewardsGiven = false;
        return currentEnemy;
    }

    /**
     * Esegue l'attacco del giocatore se il combattimento è ancora attivo.
     */
    public void playerAttack() {
        Player player = gameState.getPlayer();

        if (currentEnemy != null
                && combatManager.getCombatResult(player, currentEnemy) == CombatResult.IN_PROGRESS) {
            combatManager.playerAttack(player, currentEnemy);
        }
    }

    /**
     * Esegue l'attacco del nemico se entrambi i combattenti sono vivi.
     */
    public void enemyAttack() {
        Player player = gameState.getPlayer();

        if (currentEnemy != null && currentEnemy.isAlive() && player.isAlive()) {
            combatManager.enemyAttack(player, currentEnemy);
        }
    }

    /**
     * Restituisce il risultato del combattimento e assegna le ricompense una sola volta.
     */
    public CombatResult getCombatResult() {
        if (currentEnemy == null) {
            return CombatResult.IN_PROGRESS;
        }

        Player player = gameState.getPlayer();
        CombatResult result = combatManager.getCombatResult(player, currentEnemy);

        if (result == CombatResult.PLAYER_WON && !rewardsGiven) {
            combatManager.giveRewards(player, currentEnemy);
            rewardsGiven = true;
        }

        return result;
    }

    /**
     * Completa il piano corrente e prepara il servizio al piano successivo.
     */
    public void completeCurrentFloor() {
        if (getCurrentFloorType() == FloorType.CHECKPOINT) {
            towerManager.applyCheckpoint(gameState);
        } else {
            towerManager.advanceFloor(gameState);
        }

        currentEnemy = null;
        rewardsGiven = false;
    }

    /**
     * Indica se la torre è stata completata.
     */
    public boolean isTowerCompleted() {
        return towerManager.isTowerCompleted(gameState);
    }

    /**
     * Indica se una partita è gia stata avviata.
     */
    public boolean hasGameStarted() {
        return gameState != null;
    }
}
