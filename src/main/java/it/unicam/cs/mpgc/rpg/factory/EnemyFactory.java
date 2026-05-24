package it.unicam.cs.mpgc.rpg.factory;

import it.unicam.cs.mpgc.rpg.model.Enemy;

/**
 * DP Factory Method:
 * Interfaccia per le classi che creano nemici.
 * In questo modo il resto del gioco non dipende dalla creazione concreta dei nemici.
 */
public interface EnemyFactory {

    Enemy createRandomBasicEnemy();

    Enemy createBoss();
}