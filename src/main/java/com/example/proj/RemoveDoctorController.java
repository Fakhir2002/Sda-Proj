package com.example.proj;

import com.example.temp.DB_HANDLER.Admin_Handler;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveDoctorController {

    @FXML
    private Button docback;

    @FXML
    private Button removeDoctorButton; // Button for removing a doctor

    @FXML
    private ComboBox<String> doctorComboBox; // ComboBox to list doctors

    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private static final String SELECT_DOCTORS_QUERY = "SELECT DoctorID, Name FROM doctors";
    private Admin_Handler adminHandler = new Admin_Handler(); // Admin_Handler instance

    private ObservableList<String> doctorList = FXCollections.observableArrayList();
    private ObservableList<Integer> doctorIDList = FXCollections.observableArrayList(); // Store IDs

    public void initialize() {
        populateDoctorComboBox();
    }

    private void populateDoctorComboBox() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DOCTORS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            doctorList.clear();
            doctorIDList.clear();

            while (resultSet.next()) {
                int doctorID = resultSet.getInt("DoctorID");
                String doctorName = resultSet.getString("Name");

                doctorList.add(doctorName);
                doctorIDList.add(doctorID); // Add IDs in sync with names
            }

            doctorComboBox.setItems(doctorList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveDoctor(ActionEvent event) {
        // Get the selected doctor's index
        int selectedIndex = doctorComboBox.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) { // Ensure a doctor is selected
            int doctorID = doctorIDList.get(selectedIndex);

            // Call Admin_Handler to remove the doctor
            boolean isRemoved = Admin.removeDoctor(doctorID);

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
