package it.unicam.cs.mpgc.rpg122868.combat;

import it.unicam.cs.mpgc.rpg122868.model.Enemy;
import it.unicam.cs.mpgc.rpg122868.model.Player;

/**
 * Contiene le regole base per gestire gli attacchi in combattimento.
 */
public class CombatManager {

    private final DiceRoller diceRoller;

    public CombatManager() {
        this.diceRoller = new DiceRoller();
    }

    /**
     * Esegue un attacco del giocatore contro il nemico.
     */
    public void playerAttack(Player player, Enemy enemy) {
        int roll = diceRoller.rollD20();
        int bonus = getMainStatBonus(player);

        if (roll == 1) {
            return;
        }

        boolean hit = roll == 20 || roll + bonus >= enemy.getDefense();
        if (hit) {
            int damage = diceRoller.rollDamageDie(8) + bonus / 2;
            if (roll == 20) {
                damage *= 2;
            }
            enemy.takeDamage(damage);
        }
    }

    /**
     * Esegue un attacco del nemico contro il giocatore.
     */
    public void enemyAttack(Player player, Enemy enemy) {
        int roll = diceRoller.rollD20();

        if (roll == 1) {
            return;
        }

        boolean hit = roll == 20 || roll + enemy.getAttackBonus() >= 10;
        if (hit) {
            int damage = diceRoller.rollDamageDie(enemy.getDamageDie()) + enemy.getDamageBonus();
            if (roll == 20) {
                damage *= 2;
            }
            player.takeDamage(damage);
        }
    }

    /**
     * Restituisce il risultato del combattimento senza modificare il giocatore.
     */
    public CombatResult getCombatResult(Player player, Enemy enemy) {
        if (!player.isAlive()) {
            return CombatResult.PLAYER_LOST;
        }
        if (!enemy.isAlive()) {
            return CombatResult.PLAYER_WON;
        }
        return CombatResult.IN_PROGRESS;
    }

    /**
     * Assegna al giocatore le ricompense del nemico sconfitto.
     */
    public void giveRewards(Player player, Enemy enemy) {
        player.addExperience(enemy.getExperienceReward());
        player.addGold(enemy.getGoldReward());
    }

    /**
     * Restituisce la statistica principale usata dal giocatore per attaccare.
     */
    private int getMainStatBonus(Player player) {
        return player.getPlayerClass().getMainStatValue(player.getStats());
    }
}
