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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RemoveStaffController {
    @FXML
    private Button staffback;

    @FXML
    private Button remm; // Button for removing staff

    @FXML
    private ComboBox<String> removestaffcombobox; // ComboBox for staff names

    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private ObservableList<String> staffList = FXCollections.observableArrayList();

    private Admin_Handler adminHandler = new Admin_Handler(); // Create Admin_Handler instance

    // Method to initialize and populate the ComboBox with staff names
    @FXML
    public void initialize() {
        populateStaffComboBox();
    }

    // Method to fetch staff names from the database and populate ComboBox
    public void populateStaffComboBox() {
        removestaffcombobox.getItems().clear(); // Clear existing items from ComboBox

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT first_name FROM staff")) {

            while (resultSet.next()) {
                String staffName = resultSet.getString("first_name"); // Retrieve staff first name
                staffList.add(staffName); // Add to the list
            }

            removestaffcombobox.setItems(staffList); // Set the ComboBox items

        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions
        }
    }

    // Method to handle the remove staff action
    @FXML
    public void handleRemoveStaff(ActionEvent actionEvent) {
        // Get the selected staff name from the ComboBox
        String selectedStaffName = removestaffcombobox.getSelectionModel().getSelectedItem();

        if (selectedStaffName == null) {
            // Show warning alert if no staff is selected
            showAlert("Warning", "No staff selected for removal.", Alert.AlertType.WARNING);
            return; // Return if no staff is selected
        }

        // Call the Admin_Handler to remove the staff
        boolean success = Admin.removeStaff(selectedStaffName);

        // Show alert based on success or failure
        if (success) {
            showAlert("Success", "Staff " + selectedStaffName + " has been removed.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to remove staff " + selectedStaffName + ".", Alert.AlertType.ERROR);
        }

        // Optionally, refresh the ComboBox to reflect the removal
        populateStaffComboBox();
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to handle the back button action
    public void handlestaffback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) staffback.getScene().getWindow();

            // Create a new stage
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Handle IOExceptions
        }
    }
}
