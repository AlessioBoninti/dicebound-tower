package it.unicam.cs.mpgc.rpg.persistence;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.unicam.cs.mpgc.rpg.model.GameState;
import it.unicam.cs.mpgc.rpg.model.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Gestisce il salvataggio e il caricamento della partita su file JSON.
 */
public class SaveManager {

    private static final DateTimeFormatter SAVE_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ObjectMapper objectMapper;
    private final Path savesDirectory;

    /**
     * Prepara il gestore JSON e il percorso del file di salvataggio.
     */
    public SaveManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.savesDirectory = Paths.get("saves");
    }

    /**
     * Salva lo stato della partita nel file di salvataggio.
     */
    public void save(GameState gameState) {
        try {
            if (gameState.getSaveId() == null || gameState.getSaveId().isBlank()) {
                gameState.setSaveId(UUID.randomUUID().toString());
            }

            Files.createDirectories(savesDirectory);
            Path savePath = savesDirectory.resolve(gameState.getSaveId() + ".json");
            objectMapper.writeValue(savePath.toFile(), gameState);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio della partita.", e);
        }
    }

    /**
     * Carica lo stato della partita dal file indicato.
     */
    public GameState load(String fileName) {
        try {
            return objectMapper.readValue(savesDirectory.resolve(fileName).toFile(), GameState.class);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento della partita.", e);
        }
    }

    /**
     * Carica il salvataggio piu recente, mantenuto per compatibilita.
     */
    public GameState load() {
        List<SaveInfo> saves = listSaves();
        if (saves.isEmpty()) {
            throw new RuntimeException("Nessun salvataggio disponibile.");
        }
        return load(saves.get(0).getFileName());
    }

    /**
     * Restituisce l'elenco dei salvataggi disponibili ordinati dal piu recente.
     */
    public List<SaveInfo> listSaves() {
        List<SaveInfo> saves = new ArrayList<>();
        if (!Files.exists(savesDirectory)) {
            return saves;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(savesDirectory, "*.json")) {
            for (Path path : stream) {
                addSaveInfo(path, saves);
            }
        } catch (IOException e) {
            return saves;
        }

        saves.sort(Comparator.comparingLong(SaveInfo::getLastModified).reversed());
        return saves;
    }

    /**
     * Indica se esiste un file di salvataggio.
     */
    public boolean saveExists() {
        return !listSaves().isEmpty();
    }

    /**
     * Elimina il salvataggio indicato se presente.
     */
    public void deleteSave(String fileName) {
        try {
            Files.deleteIfExists(savesDirectory.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'eliminazione del salvataggio.", e);
        }
    }

    private void addSaveInfo(Path path, List<SaveInfo> saves) {
        try {
            GameState gameState = objectMapper.readValue(path.toFile(), GameState.class);
            Player player = gameState.getPlayer();
            if (player == null) {
                return;
            }

            File file = path.toFile();
            long lastModified = file.lastModified();
            saves.add(new SaveInfo(
                    path.getFileName().toString(),
                    player.getName(),
                    player.getPlayerClass().name(),
                    player.getLevel(),
                    gameState.getCurrentFloor(),
                    player.getGold(),
                    lastModified,
                    formatLastModified(lastModified)
            ));
        } catch (IOException | RuntimeException ignored) {
        }
    }

    private String formatLastModified(long lastModified) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(lastModified),
                ZoneId.systemDefault()
        );
        return dateTime.format(SAVE_DATE_FORMATTER);
    }
}
