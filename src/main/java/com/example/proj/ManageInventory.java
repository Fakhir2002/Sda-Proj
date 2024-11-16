package com.example.proj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageInventory extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageInventory.class.getResource("Manage-Inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 430);
        stage.setTitle("Manage Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}