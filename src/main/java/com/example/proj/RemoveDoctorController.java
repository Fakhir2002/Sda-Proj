package com.example.proj;

import Database.Admin_Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class RemoveDoctorController {

    @FXML
    private Button docback;

    @FXML
    private Button removeDoctorButton; // Button for removing a doctor

    @FXML
    private ComboBox<String> doctorComboBox; // ComboBox to list doctors

    private Admin_Handler adminHandler = new Admin_Handler(); // Admin_Handler instance

    private ObservableList<String> doctorList = FXCollections.observableArrayList();
    private ObservableList<Integer> doctorIDList = FXCollections.observableArrayList(); // Store IDs

    public void initialize() {
        populateDoctorComboBox();
    }

    private void populateDoctorComboBox() {
        // Fetch doctors list using Admin_Handler instead of database code here
        doctorList.clear();
        doctorIDList.clear();

        // Fetch doctor names and IDs from the handler
        var doctorData = adminHandler.getDoctorNamesAndIDs();

        for (Pair<String, Integer> data : doctorData) {
            doctorList.add(data.getKey());
            doctorIDList.add(data.getValue());
        }



        doctorComboBox.setItems(doctorList);
    }

    @FXML
    private void handleRemoveDoctor(ActionEvent event) {
        // Get the selected doctor's index
        int selectedIndex = doctorComboBox.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) { // Ensure a doctor is selected
            int doctorID = doctorIDList.get(selectedIndex);

            // Call Admin_Handler to remove the doctor
            boolean isRemoved = adminHandler.removeDoctor(doctorID);

            if (isRemoved) {
                showAlert("Success", "Doctor removed successfully!", Alert.AlertType.INFORMATION);

                // Remove doctor from ComboBox and ID list
                doctorList.remove(selectedIndex);
                doctorIDList.remove(selectedIndex);
                doctorComboBox.setItems(doctorList);

            } else {
                showAlert("Error", "Failed to remove the doctor.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select a doctor to remove.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handledocback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) docback.getScene().getWindow();

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
