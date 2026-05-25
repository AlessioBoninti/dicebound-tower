package it.unicam.cs.mpgc.rpg.factory;

import it.unicam.cs.mpgc.rpg.model.Enemy;

/**
 *
 * DP Factory Method:
 * Factory preparata per una possibile Torre delle Ombre (Seconda Torre).
 * Per ora non viene usata nella prima versione del gioco,
 * ma mostra come si potranno aggiungere nuove torri in futuro
 * senza modificare il sistema di combattimento.
 */
public class ShadowTowerEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createRandomBasicEnemy() {
        throw new UnsupportedOperationException("Torre delle Ombre non disponibile nella prima release.");
    }

    @Override
    public Enemy createBoss() {
        throw new UnsupportedOperationException("Torre delle Ombre non disponibile nella prima release.");
    }
}