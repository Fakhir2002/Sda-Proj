package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {
    public Button hello;
    @FXML
    private Button AdminLogin;
    public void adminback(ActionEvent actionEvent) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) hello.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handleAdminLogin(ActionEvent actionEvent) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) AdminLogin.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
