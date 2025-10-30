package com.example.fxprojedemo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {

    private TextArea textArea;
    private File currentFile = null;
    private boolean isChanged = false;

    @Override
    public void start(Stage primaryStage) {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.textProperty().addListener((obs, oldText, newText) -> isChanged = true);

        // Menu Bar
        MenuBar menuBar = new MenuBar();

        // "File" Menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New File");
        MenuItem openFile = new MenuItem("Open File");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem saveAsFile = new MenuItem("Save As");
        MenuItem exitApp = new MenuItem("Log Out");

        fileMenu.getItems().addAll(newFile, openFile, saveFile, saveAsFile, new SeparatorMenuItem(), exitApp);
        menuBar.getMenus().add(fileMenu);

        // Menü eylemleri
        newFile.setOnAction(e -> newFile());
        openFile.setOnAction(e -> openFile(primaryStage));
        saveFile.setOnAction(e -> saveFile(primaryStage, false));
        saveAsFile.setOnAction(e -> saveFile(primaryStage, true));
        exitApp.setOnAction(e -> exitApp(primaryStage));

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(textArea);
        root.setId("root");

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Mini Not Defteri");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Yeni dosya
    private void newFile() {
        if (confirmUnsavedChanges()) {
            textArea.clear();
            currentFile = null;
            isChanged = false;
        }
    }

    // Dosya aç
    private void openFile(Stage stage) {
        if (!confirmUnsavedChanges()) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }
                currentFile = file;
                isChanged = false;
            } catch (IOException ex) {
                showAlert("Error", "Couldn't Open The File: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // Dosya kaydet (normal veya farklı kaydet)
    private void saveFile(Stage stage, boolean saveAs) {
        if (currentFile == null || saveAs) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                currentFile = file;
            } else return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            writer.write(textArea.getText());
            isChanged = false;
        } catch (IOException ex) {
            showAlert("Error", "Couldn't Save The File: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Kaydedilmemiş değişiklik uyarısı
    private boolean confirmUnsavedChanges() {
        if (!isChanged) return true;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("The Changes That Weren't Saved");
        alert.setHeaderText("There Are Some Changes Weren't Saved.");
        alert.setContentText("Do you want to save?");

        ButtonType save = new ButtonType("Save");
        ButtonType dontSave = new ButtonType("Don't save");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(save, dontSave, cancel);
        var result = alert.showAndWait();

        if (result.get() == save) {
            saveFile(null, false);
            return true;
        } else return result.get() == dontSave;
    }

    // Çıkış işlemi
    private void exitApp(Stage stage) {
        if (confirmUnsavedChanges()) {
            stage.close();
        }
    }

    // Uyarı penceresi
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
