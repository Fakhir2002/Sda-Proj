package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AllocateResourcesController implements InitializeUsername{

    @FXML
    private Button back;
    @FXML
    private Button Confirmation;
    @FXML
    private TableView<Emergency> emergencyTable; // TableView to display emergency data
    @FXML
    private TableColumn<Emergency, Integer> columnId;
    @FXML
    private TableColumn<Emergency, Integer> columnPatientid;
    @FXML
    private TableColumn<Emergency, Integer> columnHospitalid;
    @FXML
    private TableColumn<Emergency, String> columnType;
    @FXML
    private TableColumn<Emergency, String> columnStatus;
    @FXML
    private Staff currentStaff;

    @Override
    public void initialize(String username) {
        currentStaff = new Staff(username);

    }


    public void ConfirmThis(ActionEvent actionEvent) {
        // Get the selected emergency from the TableView
        Emergency selectedEmergency = emergencyTable.getSelectionModel().getSelectedItem();

        if (selectedEmergency == null) {
            // No emergency selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Emergency Selected");
            alert.setContentText("Please select an emergency to confirm allocation.");
            alert.showAndWait();
            return;
        }

        // Update the status to "ALLOCATED"
        boolean isUpdated = selectedEmergency.updateStatus(selectedEmergency.getEmergency_id(), "ALLOCATED");

        if (isUpdated) {
            // Update the TableView to reflect the change
            selectedEmergency.setStatus("ALLOCATED");
            emergencyTable.refresh();

            // Show a confirmation alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Status Updated");
            alert.setContentText("The emergency status has been updated to 'ALLOCATED'.");
            alert.showAndWait();
        } else {
            // Show an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Update Failed");
            alert.setContentText("Failed to update the emergency status. Please try again.");
            alert.showAndWait();
        }
    }


    public void GoBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml")); // Ensure StaffHome.fxml exists
            Parent newPage = loader.load();
            StaffHomeController controller = loader.getController();
            controller.initialize(currentStaff.getUsername());

            Stage currentStage = (Stage) back.getScene().getWindow();

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Go Back");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Configure TableView columns to match Emergency fields
        columnId.setCellValueFactory(new PropertyValueFactory<>("emergency_id"));
        columnPatientid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        columnHospitalid.setCellValueFactory(new PropertyValueFactory<>("hospital_id"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load emergency data
        loadEmergencyData();
    }

    private void loadEmergencyData() {
        List<Emergency> emergencies = Emergency.fetchEmergencyData(); // Fetch emergency data
        emergencyTable.getItems().clear();
        emergencyTable.getItems().addAll(emergencies); // Add data to TableView
    }


}
