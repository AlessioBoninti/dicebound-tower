package it.unicam.cs.mpgc.rpg.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.unicam.cs.mpgc.rpg.model.GameState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gestisce il salvataggio e il caricamento della partita su file JSON.
 */
public class SaveManager {

    private final ObjectMapper objectMapper;
    private final Path savePath;

    /**
     * Prepara il gestore JSON e il percorso del file di salvataggio.
     */
    public SaveManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.savePath = Paths.get("savegame.json");
    }

    /**
     * Salva lo stato della partita nel file di salvataggio.
     */
    public void save(GameState gameState) {
        try {
            objectMapper.writeValue(savePath.toFile(), gameState);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio della partita.", e);
        }
    }

    /**
     * Carica lo stato della partita dal file di salvataggio.
     */
    public GameState load() {
        try {
            return objectMapper.readValue(savePath.toFile(), GameState.class);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento della partita.", e);
        }
    }

    /**
     * Indica se esiste un file di salvataggio.
     */
    public boolean saveExists() {
        return Files.exists(savePath);
    }
}
