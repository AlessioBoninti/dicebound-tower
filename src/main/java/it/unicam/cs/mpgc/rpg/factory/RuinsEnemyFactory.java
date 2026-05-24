package it.unicam.cs.mpgc.rpg.factory;

import it.unicam.cs.mpgc.rpg.model.Enemy;

import java.util.Random;

/**
 * DP Factory Method:
 * Factory dei nemici della Torre delle Rovine (Prima Torre).
 * Centralizza la creazione dei nemici specifici di questa torre.
 */
public class RuinsEnemyFactory implements EnemyFactory {

    private final Random random;

    public RuinsEnemyFactory() {
        this.random = new Random();
    }

    @Override
    public Enemy createRandomBasicEnemy() {
        int choice = random.nextInt(3);

        if (choice == 0) {
            return createGoblin();
        }
        if (choice == 1) {
            return createSkeletonWarrior();
        }
        return createDarkMage();
    }

    @Override
    public Enemy createBoss() {
        return createAncientGoblinKing();
    }

    public Enemy createGoblin() {
        return new Enemy("Goblin", 30, 10, 3, 6, 2, 30, 10);
    }

    public Enemy createSkeletonWarrior() {
        return new Enemy("Skeleton Warrior", 40, 11, 4, 8, 2, 45, 15);
    }

    public Enemy createDarkMage() {
        return new Enemy("Dark Mage", 35, 10, 5, 8, 3, 50, 20);
    }

    public Enemy createAncientGoblinKing() {
        return new Enemy("Ancient Goblin King", 95, 13, 6, 10, 4, 120, 80);
    }
}