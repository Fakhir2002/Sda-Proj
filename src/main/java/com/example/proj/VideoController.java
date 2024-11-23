package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VideoController implements InitializeUsername{
    @FXML
    private Label welcomeText;
    public Button backfromvid;

    @FXML
    private Doctor currentDoctor;

    @FXML
    public void initialize(String username) {
        currentDoctor = new Doctor(username);
    }



    public void handlebackvid(ActionEvent actionEvent) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            DoctorHomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) backfromvid.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}