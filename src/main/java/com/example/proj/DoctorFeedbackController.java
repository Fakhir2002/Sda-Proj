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

public class DoctorFeedbackController extends Application {
@FXML
private Button Backbutton;




    public void GoBack(ActionEvent actionEvent) {   try {
        // Load the FXML for the About Us application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml")); // Ensure AboutUs.fxml exists in the same directory
        Parent newPage = loader.load();

        Stage currentStage = (Stage) Backbutton.getScene().getWindow();

        // Create a new stage

        currentStage.setScene(new Scene(newPage));
        currentStage.setTitle("Doctor Home Page");
        currentStage.sizeToScene();
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Debugging in case of issues loading the FXML
    }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
