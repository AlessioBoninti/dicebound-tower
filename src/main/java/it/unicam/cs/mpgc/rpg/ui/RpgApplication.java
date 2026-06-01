package it.unicam.cs.mpgc.rpg.ui;

import it.unicam.cs.mpgc.rpg.combat.CombatResult;
import it.unicam.cs.mpgc.rpg.model.Enemy;
import it.unicam.cs.mpgc.rpg.model.Player;
import it.unicam.cs.mpgc.rpg.model.PlayerClass;
import it.unicam.cs.mpgc.rpg.service.GameService;
import it.unicam.cs.mpgc.rpg.tower.FloorType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;

public class RpgApplication extends Application {

    private final GameService gameService = new GameService();
    private Stage primaryStage;
    private TextArea logArea;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Dicebound Tower");
        showMainMenu();
    }

    private void showMainMenu() {
        Label title = createTitle("Dicebound Tower");
        Label subtitle = new Label("Un piccolo gioco di ruolo a turni");
        subtitle.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 16;");

        Button newGameButton = createMenuButton("Nuova partita");
        Button loadGameButton = createMenuButton("Carica partita");
        Button exitButton = createMenuButton("Esci");

        newGameButton.setOnAction(event -> showCharacterCreation());
        loadGameButton.setOnAction(event -> {
            if (gameService.saveExists()) {
                gameService.loadGame();
                showTowerSelection();
            } else {
                appendLog("Nessun salvataggio trovato.");
            }
        });
        exitButton.setOnAction(event -> primaryStage.close());

        logArea = createLogArea();

        VBox root = new VBox(15, title, subtitle, newGameButton, loadGameButton, exitButton, logArea);
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #1f2933;");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void showCharacterCreation() {
        Label title = createTitle("Creazione personaggio");

        ImageView classImageView = new ImageView();
        classImageView.setFitWidth(220);
        classImageView.setFitHeight(220);
        classImageView.setPreserveRatio(true);

        Label placeholderLabel = new Label();
        placeholderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24; -fx-font-weight: bold;");

        StackPane spriteArea = new StackPane(classImageView, placeholderLabel);
        spriteArea.setPrefSize(260, 260);
        spriteArea.setMaxSize(260, 260);
        spriteArea.setStyle(
                "-fx-background-color: #273642;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #52616b;"
                        + "-fx-border-radius: 12;"
                        + "-fx-padding: 20;"
        );

        TextField nameField = new TextField();
        nameField.setPromptText("Nome personaggio");
        nameField.setMaxWidth(300);

        ComboBox<PlayerClass> classComboBox = new ComboBox<>();
        classComboBox.getItems().addAll(PlayerClass.WARRIOR, PlayerClass.MAGE, PlayerClass.ROGUE);
        classComboBox.setValue(PlayerClass.WARRIOR);
        classComboBox.setPrefWidth(220);

        Label descriptionLabel = new Label(getClassDescription(classComboBox.getValue()));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(500);
        descriptionLabel.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");
        updateClassPreview(classComboBox.getValue(), classImageView, placeholderLabel);
        classComboBox.setOnAction(event -> {
            PlayerClass selectedClass = classComboBox.getValue();
            descriptionLabel.setText(getClassDescription(selectedClass));
            updateClassPreview(selectedClass, classImageView, placeholderLabel);
        });

        Button startButton = createMenuButton("Inizia avventura");
        Button backButton = createMenuButton("Indietro");

        startButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                name = "Eroe";
            }

            PlayerClass playerClass = classComboBox.getValue();
            gameService.startNewGame(name, playerClass);
            appendLog("Nuova partita iniziata.");
            showTowerSelection();
        });
        backButton.setOnAction(event -> showMainMenu());

        HBox buttons = new HBox(15, startButton, backButton);
        buttons.setStyle("-fx-alignment: center;");

        VBox details = new VBox(15, descriptionLabel, classComboBox, nameField, buttons);
        details.setStyle("-fx-alignment: center-left;");

        HBox content = new HBox(30, spriteArea, details);
        content.setStyle("-fx-alignment: center;");

        VBox root = new VBox(25, title, content);
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #1f2933;");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void showTowerSelection() {
        Label title = createTitle("Selezione torre");

        Player player = gameService.getGameState().getPlayer();
        Label playerInfo = new Label(
                "Nome: " + player.getName()
                        + " | Classe: " + player.getPlayerClass()
                        + " | Livello: " + player.getLevel()
                        + " | HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                        + " | Oro: " + player.getGold()
        );
        playerInfo.setWrapText(true);
        playerInfo.setMaxWidth(650);
        playerInfo.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Button ruinsTowerButton = createMenuButton("Torre delle Rovine");
        Button shadowsTowerButton = createMenuButton("Torre delle Ombre - bloccata");
        Button frostTowerButton = createMenuButton("Torre del Gelo - bloccata");
        Button saveButton = createMenuButton("Salva partita");
        Button menuButton = createMenuButton("Torna al menu");

        shadowsTowerButton.setDisable(true);
        frostTowerButton.setDisable(true);

        ruinsTowerButton.setOnAction(event -> showExploration());
        saveButton.setOnAction(event -> {
            gameService.saveGame();
            appendLog("Partita salvata.");
        });
        menuButton.setOnAction(event -> showMainMenu());

        logArea = createLogArea();

        VBox root = new VBox(
                15,
                title,
                playerInfo,
                ruinsTowerButton,
                shadowsTowerButton,
                frostTowerButton,
                saveButton,
                menuButton,
                logArea
        );
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #1f2933;");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void showExploration() {
        Label title = createTitle("Torre delle Rovine");

        Player player = gameService.getGameState().getPlayer();
        Label playerInfo = new Label(
                "Nome: " + player.getName()
                        + " | Classe: " + player.getPlayerClass()
                        + " | Livello: " + player.getLevel()
                        + " | HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                        + " | Oro: " + player.getGold()
        );
        playerInfo.setWrapText(true);
        playerInfo.setMaxWidth(650);
        playerInfo.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Label floorLabel = new Label(
                "Piano corrente: " + gameService.getGameState().getCurrentFloor()
                        + " (" + gameService.getCurrentFloorType() + ")"
        );
        floorLabel.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Enemy currentEnemy = gameService.getCurrentEnemy();
        Label enemyLabel;
        if (currentEnemy == null) {
            enemyLabel = new Label("Nessun nemico presente");
        } else {
            enemyLabel = new Label(
                    currentEnemy.getName()
                            + " - HP: " + currentEnemy.getCurrentHp() + "/" + currentEnemy.getMaxHp()
            );
        }
        enemyLabel.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Button proceedButton = createMenuButton("Procedi");
        Button potionButton = createMenuButton("Usa pozione");
        Button saveButton = createMenuButton("Salva partita");
        Button towerSelectionButton = createMenuButton("Torna alla selezione torri");

        proceedButton.setOnAction(event -> {
            if (!gameService.hasGameStarted()) {
                appendLog("Nessuna partita avviata.");
                return;
            }

            FloorType floorType = gameService.getCurrentFloorType();
            if (floorType == FloorType.BASIC_ENEMY) {
                Enemy enemy = gameService.prepareEnemyForCurrentFloor();
                appendLog("È apparso: " + enemy.getName());
                showCombat();
            } else if (floorType == FloorType.BOSS) {
                Enemy enemy = gameService.prepareEnemyForCurrentFloor();
                appendLog("Boss apparso: " + enemy.getName());
                showCombat();
            } else if (floorType == FloorType.CHECKPOINT) {
                gameService.completeCurrentFloor();
                appendLog("Checkpoint raggiunto. Il personaggio recupera HP e avanza al piano successivo.");
                showExploration();
            } else if (floorType == FloorType.COMPLETED) {
                appendLog("Torre completata.");
            }
        });

        potionButton.setOnAction(event -> {
            if (gameService.hasGameStarted()) {
                Player currentPlayer = gameService.getGameState().getPlayer();
                boolean potionUsed = currentPlayer.getInventory().useHealingPotion(currentPlayer);
                if (potionUsed) {
                    appendLog("Pozione usata.");
                } else {
                    appendLog("Nessuna pozione disponibile.");
                }
                showExploration();
            }
        });

        saveButton.setOnAction(event -> {
            if (gameService.hasGameStarted()) {
                gameService.saveGame();
                appendLog("Partita salvata.");
            }
        });

        towerSelectionButton.setOnAction(event -> showTowerSelection());

        HBox actionButtons = new HBox(15, proceedButton, potionButton);
        actionButtons.setStyle("-fx-alignment: center;");

        HBox utilityButtons = new HBox(15, saveButton, towerSelectionButton);
        utilityButtons.setStyle("-fx-alignment: center;");

        logArea = createLogArea();

        VBox root = new VBox(
                15,
                title,
                playerInfo,
                floorLabel,
                enemyLabel,
                actionButtons,
                utilityButtons,
                logArea
        );
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #1f2933;");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void showCombat() {
        Label title = createTitle("Combattimento");

        Player player = gameService.getGameState().getPlayer();
        Enemy enemy = gameService.getCurrentEnemy();
        CombatResult combatResult = enemy == null ? CombatResult.IN_PROGRESS : gameService.getCombatResult();

        Label playerInfo = new Label(
                player.getName()
                        + " | Classe: " + player.getPlayerClass()
                        + " | Livello: " + player.getLevel()
                        + " | HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
        );
        playerInfo.setWrapText(true);
        playerInfo.setMaxWidth(650);
        playerInfo.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Label enemyInfo;
        if (enemy == null) {
            enemyInfo = new Label("Nessun nemico presente");
        } else {
            enemyInfo = new Label(enemy.getName() + " | HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp());
        }
        enemyInfo.setWrapText(true);
        enemyInfo.setMaxWidth(650);
        enemyInfo.setStyle("-fx-text-fill: #d9e2ec; -fx-font-size: 15;");

        Label playerSprite = new Label("Player");
        playerSprite.setStyle("-fx-text-fill: white; -fx-font-size: 22; -fx-font-weight: bold;");
        StackPane playerBox = new StackPane(playerSprite);
        playerBox.setPrefSize(220, 220);
        playerBox.setStyle(
                "-fx-background-color: #273642;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #52616b;"
                        + "-fx-border-radius: 12;"
        );

        Label enemySprite = new Label(enemy == null ? "Nemico" : enemy.getName());
        enemySprite.setWrapText(true);
        enemySprite.setStyle("-fx-text-fill: white; -fx-font-size: 22; -fx-font-weight: bold;");
        StackPane enemyBox = new StackPane(enemySprite);
        enemyBox.setPrefSize(220, 220);
        enemyBox.setStyle(
                "-fx-background-color: #3a2f35;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #7b5c64;"
                        + "-fx-border-radius: 12;"
        );

        HBox combatants = new HBox(60, playerBox, enemyBox);
        combatants.setStyle("-fx-alignment: center;");

        Button attackButton = createMenuButton("Attacca");
        Button potionButton = createMenuButton("Usa pozione");
        Button continueButton = createMenuButton("Continua");

        attackButton.setDisable(enemy == null || combatResult != CombatResult.IN_PROGRESS);
        potionButton.setDisable(enemy == null || combatResult != CombatResult.IN_PROGRESS);
        continueButton.setDisable(combatResult != CombatResult.PLAYER_WON);

        attackButton.setOnAction(event -> {
            if (gameService.getCurrentEnemy() == null) {
                appendLog("Nessun nemico presente.");
                return;
            }

            gameService.playerAttack();
            CombatResult result = gameService.getCombatResult();

            if (result == CombatResult.IN_PROGRESS) {
                gameService.enemyAttack();
                result = gameService.getCombatResult();
            }

            if (result == CombatResult.PLAYER_WON) {
                appendLog("Nemico sconfitto.");
            } else if (result == CombatResult.PLAYER_LOST) {
                appendLog("Sei stato sconfitto.");
            }

            showCombat();
        });

        potionButton.setOnAction(event -> {
            Player currentPlayer = gameService.getGameState().getPlayer();
            boolean potionUsed = currentPlayer.getInventory().useHealingPotion(currentPlayer);
            if (potionUsed) {
                appendLog("Pozione usata.");
            } else {
                appendLog("Nessuna pozione disponibile.");
            }
            showCombat();
        });

        continueButton.setOnAction(event -> {
            gameService.completeCurrentFloor();
            showExploration();
        });

        HBox buttons = new HBox(15, attackButton, potionButton, continueButton);
        buttons.setStyle("-fx-alignment: center;");

        logArea = createLogArea();

        VBox root = new VBox(15, title, playerInfo, enemyInfo, combatants, buttons, logArea);
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #1f2933;");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private Label createTitle(String text) {
        Label title = new Label(text);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 28; -fx-font-weight: bold;");
        return title;
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setStyle(
                "-fx-background-color: #52616b;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-size: 14;"
                        + "-fx-padding: 8 14;"
        );
        return button;
    }

    private String getClassDescription(PlayerClass playerClass) {
        if (playerClass == PlayerClass.WARRIOR) {
            return "Warrior\n\nSTR: 8\nDEX: 4\nINT: 2\nVIT: 8\n\nClasse resistente basata sulla forza.";
        }
        if (playerClass == PlayerClass.MAGE) {
            return "Mage\n\nSTR: 2\nDEX: 4\nINT: 9\nVIT: 5\n\nClasse fragile ma forte con la magia.";
        }
        return "Rogue\n\nSTR: 5\nDEX: 9\nINT: 3\nVIT: 4\n\nClasse veloce basata sulla destrezza.";
    }

    private String getClassImagePath(PlayerClass playerClass) {
        if (playerClass == PlayerClass.WARRIOR) {
            return "/images/warrior_front.png";
        }
        if (playerClass == PlayerClass.MAGE) {
            return "/images/mage_front.png";
        }
        return "/images/rogue_front.png";
    }

    private void updateClassPreview(PlayerClass playerClass, ImageView imageView, Label placeholderLabel) {
        InputStream imageStream = getClass().getResourceAsStream(getClassImagePath(playerClass));

        if (imageStream != null) {
            imageView.setImage(new Image(imageStream));
            imageView.setVisible(true);
            imageView.setManaged(true);
            placeholderLabel.setVisible(false);
            placeholderLabel.setManaged(false);
        } else {
            imageView.setImage(null);
            imageView.setVisible(false);
            imageView.setManaged(false);
            placeholderLabel.setText(playerClass.name());
            placeholderLabel.setVisible(true);
            placeholderLabel.setManaged(true);
        }
    }

    private void appendLog(String message) {
        if (logArea != null) {
            logArea.appendText(message + System.lineSeparator());
        }
    }

    private TextArea createLogArea() {
        String previousLog = logArea == null ? "" : logArea.getText();
        TextArea area = new TextArea(previousLog);
        area.setEditable(false);
        area.setPromptText("Log di gioco...");
        area.setMaxHeight(120);
        area.setPrefHeight(120);
        area.setMaxWidth(600);
        area.setPrefWidth(600);
        area.setStyle(
                "-fx-control-inner-background: #111827;"
                        + "-fx-text-fill: #e5e7eb;"
                        + "-fx-highlight-fill: #52616b;"
                        + "-fx-highlight-text-fill: white;"
                        + "-fx-font-size: 13;"
        );
        return area;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
