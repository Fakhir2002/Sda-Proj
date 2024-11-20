package com.example.proj;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AllocateResourcesController extends Application {
    @FXML
    private Button back;
private Button Confirmation;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
    }

    public void ConfirmThis(ActionEvent actionEvent) {try {
        // Load the FXML for the About Us application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllocateResourcesController.fxml")); // Ensure AboutUs.fxml exists in the same directory
        Parent newPage = loader.load();

        Stage currentStage = (Stage) Confirmation.getScene().getWindow();

        // Create a new stage

        currentStage.setScene(new Scene(newPage));
        currentStage.setTitle("Allocate Resources");
        currentStage.sizeToScene();
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Debugging in case of issues loading the FXML
    }

    }

    public void GoBack(ActionEvent actionEvent) { try {
        // Load the FXML for the About Us application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml")); // Ensure AboutUs.fxml exists in the same directory
        Parent newPage = loader.load();

        Stage currentStage = (Stage) back.getScene().getWindow();

        // Create a new stage

        currentStage.setScene(new Scene(newPage));
        currentStage.setTitle("go back");
        currentStage.sizeToScene();
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Debugging in case of issues loading the FXML
    }

    }
}
