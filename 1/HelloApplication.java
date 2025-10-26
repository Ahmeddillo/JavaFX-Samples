package com.example.fxprojedemo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI öğeleri
        Label greetingLabel = new Label("Hello! Write Your Name Please:");
        TextField nameField = new TextField();
        Button greetButton = new Button("Hello");

        // Buton aksiyonu
        greetButton.setOnAction(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                greetingLabel.setText("Hello, " + name + "!");
            } else {
                greetingLabel.setText("Please enter your name!.");
            }
        });

        // Layout
        VBox root = new VBox(35);
        root.getChildren().addAll(greetingLabel, nameField, greetButton);
        root.setId("root");  // CSS için id atadık

        // Scene ve CSS
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("JavaFX CSS sample");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

