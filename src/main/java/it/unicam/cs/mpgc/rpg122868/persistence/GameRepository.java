package it.unicam.cs.mpgc.rpg122868.persistence;

import it.unicam.cs.mpgc.rpg122868.model.GameState;

import java.util.List;

/**
 * Astrazione per la persistenza dello stato di gioco.
 */
public interface GameRepository {

    void save(GameState gameState);

    GameState load();

    GameState load(String fileName);

    List<SaveInfo> listSaves();

    boolean saveExists();

    void deleteSave(String fileName);
}
