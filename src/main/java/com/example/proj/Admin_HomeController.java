package com.example.proj;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Admin_HomeController {


    @FXML
    private Button Adminlogout;
    @FXML
    private Button removedoc;
    @FXML
    private Button removepatient;
    @FXML
    private Button removestaff;
    @FXML
    private Button healthcare;
    @FXML
    private Button addHospital;

    public void handleAdminlogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) Adminlogout.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handlepackages(ActionEvent actionEvent) {

    }

    public void handleremovedoc(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_RemoveDoctor.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) removedoc.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Remove Doctor");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handleremovepatient(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_RemovePatient.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) removepatient.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Remove Patient");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handleremovestaff(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_RemoveStaff.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) removestaff.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Remove Staff");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handlehealthcare(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHealthcarePackages.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) healthcare.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Healthcare Packages");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handleAddHospital(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addHospital.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) addHospital.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Add Hospital");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }


    }
}
