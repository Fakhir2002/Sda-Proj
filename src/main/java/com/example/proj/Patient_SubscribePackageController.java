package com.example.proj;

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

public class Patient_SubscribePackageController implements InitializeUsername {
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

    private final HealthCarePackages handler = new HealthCarePackages();
    private final Bill payment = new Bill(); // Instance of the PayBill_Handler
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
        ObservableList<HealthCarePackages> packageList = FXCollections.observableArrayList(handler.getAllPackages());

        // Populate the TableView with the fetched data
        tableView.setItems(packageList);
    }

    @FXML
    public void proceedWithPackage(ActionEvent event) {
        // Get the selected package from the TableView
        selectedPackage = tableView.getSelectionModel().getSelectedItem();

        if (selectedPackage != null) {
            // Create a payment for the selected package
            String description = "HealthCare Package: " + selectedPackage.getName();
            double amount = selectedPackage.getPrice();
            String status = "Unpaid";

            // Add payment to the database
            boolean paymentAdded = payment.addPayment(description, amount, status);

            if (paymentAdded) {
                // Payment successfully added to the database
                System.out.println("Payment created for the package: " + selectedPackage.getName());

                // Optionally, you can display a success message to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment created successfully!", ButtonType.OK);
                alert.showAndWait();
            } else {
                // Handle failure in payment creation
                System.out.println("Failed to create payment!");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to create payment.", ButtonType.OK);
                alert.showAndWait();
            }

            // Refresh the TableView to apply the new row style
            tableView.refresh();
        } else {
            // No package selected, display a message
            System.out.println("No package selected!");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a package to proceed.", ButtonType.OK);
            alert.showAndWait();
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
