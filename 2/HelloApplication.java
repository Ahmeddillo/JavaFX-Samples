package com.example.fxprojedemo2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private int count = 0; // Başlangıç değeri

    @Override
    public void start(Stage primaryStage) {
        // Label — sayıyı gösterecek
        Label counterLabel = new Label(String.valueOf(count));
        counterLabel.setId("counter-label");

        // Butonlar
        Button increaseButton = new Button("Artır");
        Button decreaseButton = new Button("Azalt");

        // Buton olayları
        increaseButton.setOnAction(e -> {
            if (count < 100) {
                count++;
                counterLabel.setText(String.valueOf(count));
            }
        });

        decreaseButton.setOnAction(e -> {
            if (count > 0) {
                count--;
                counterLabel.setText(String.valueOf(count));
            }
        });

        // Layoutlar
        HBox buttonBox = new HBox(15, decreaseButton, increaseButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, counterLabel, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setId("root");

        // Scene
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Stage
        primaryStage.setTitle("JavaFX Sayaç Uygulaması");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
