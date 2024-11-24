package com.example.proj;

import com.example.temp.DB_HANDLER.HealthCarePackage_Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;

import java.io.IOException;

public class SubscribePackageController implements InitializeUsername {
    @FXML
    private TableView<HealthCarePackages> tableView;

    @FXML
    private TableColumn<HealthCarePackages, String> columnName;

    @FXML
    private TableColumn<HealthCarePackages, String> columnHospitalName;

    @FXML
    private TableColumn<HealthCarePackages, String> columnStartDate;

    @FXML
    private TableColumn<HealthCarePackages, String> columnEndDate;

    @FXML
    private TableColumn<HealthCarePackages, Double> columnPrice;

    @FXML
    private TableColumn<HealthCarePackages, String> columnDescription;

    @FXML
    private Button muni; // Back button

    @FXML
    private Button proceedButton; // Proceed button

    @FXML
    private Patient currentPatient;

    private final HealthCarePackage_Handler dbHandler = new HealthCarePackage_Handler();
    private HealthCarePackages selectedPackage; // Holds the package selected for proceeding

    @FXML
    public void initialize(String username) {
        // Initialize the current patient based on the username
        currentPatient = new Patient(username);

        // Bind columns to properties in the HealthCarePackages class
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnHospitalName.setCellValueFactory(new PropertyValueFactory<>("hospitalName"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Set custom row factory for dynamic styling
        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(HealthCarePackages item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.equals(selectedPackage)) {
                    setStyle("-fx-background-color: lightgreen;"); // Set the green background
                } else {
                    setStyle(""); // Reset style for unselected rows
                }
            }
        });

        // Load data from the database
        loadData();
    }

    private void loadData() {
        // Fetch data from the database using the handler
        ObservableList<HealthCarePackages> packageList = FXCollections.observableArrayList(dbHandler.getAllPackages());

        // Populate the TableView with the fetched data
        tableView.setItems(packageList);
    }

    @FXML
    public void proceedWithPackage(ActionEvent event) {
        // Get the selected package from the TableView
        selectedPackage = tableView.getSelectionModel().getSelectedItem();

        if (selectedPackage != null) {
            // Refresh the TableView to apply the new row style
            tableView.refresh();

            // Optional: Additional actions like saving the selected package to the database
            System.out.println("Proceeding with package: " + selectedPackage.getName());
        } else {
            // No package selected, display a message
            System.out.println("No package selected!");
        }
    }

    @FXML
    public void backchal(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the Patient Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            // Pass the username to the PatientHomeController
            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) muni.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Patient Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log any exceptions for debugging
        }
    }
}
