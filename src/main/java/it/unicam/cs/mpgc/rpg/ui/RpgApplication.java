package it.unicam.cs.mpgc.rpg.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RpgApplication extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("Dicebound Tower");
        Label subtitle = new Label("Progetto RPG Java - MPGC");

        VBox root = new VBox(10, title, subtitle);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Dicebound Tower");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}