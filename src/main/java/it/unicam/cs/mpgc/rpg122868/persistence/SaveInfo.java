package it.unicam.cs.mpgc.rpg122868.persistence;

/**
 * Contiene i dati essenziali di una partita salvata da mostrare nella GUI.
 */
public class SaveInfo {

    private final String fileName;
    private final String playerName;
    private final String playerClass;
    private final int playerLevel;
    private final int currentFloor;
    private final int gold;
    private final long lastModified;
    private final String formattedLastModified;

    public SaveInfo(String fileName, String playerName, String playerClass, int playerLevel,
                    int currentFloor, int gold, long lastModified, String formattedLastModified) {
        this.fileName = fileName;
        this.playerName = playerName;
        this.playerClass = playerClass;
        this.playerLevel = playerLevel;
        this.currentFloor = currentFloor;
        this.gold = gold;
        this.lastModified = lastModified;
        this.formattedLastModified = formattedLastModified;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getGold() {
        return gold;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getFormattedLastModified() {
        return formattedLastModified;
    }
}
