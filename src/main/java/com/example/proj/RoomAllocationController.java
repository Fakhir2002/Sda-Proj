package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomAllocationController implements InitializeUsername{
    @FXML
    private Label welcomeText;

    @FXML
    private Button BackButton;

    @FXML
    private Staff currentStaff;

    @Override
    public void initialize(String username) {
        currentStaff = new Staff(username);
    }


    public void HandleBack(ActionEvent actionEvent) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();
            StaffHomeController controller = loader.getController();
            controller.initialize(currentStaff.getUsername());


            Stage currentStage = (Stage) BackButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Staff Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
