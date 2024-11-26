package com.example.proj;

import Database.HealthCarePackage_Handler;
import Database.Hospital_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Admin_HealthcarePackageController {

    @FXML
    private TableView<HealthCarePackages> packagesTable;
    @FXML
    private TableColumn<HealthCarePackages, String> nameColumn;
    @FXML
    private TableColumn<HealthCarePackages, String> hospitalNameColumn;
    @FXML
    private TableColumn<HealthCarePackages, LocalDate> startDateColumn;
    @FXML
    private TableColumn<HealthCarePackages, LocalDate> endDateColumn;
    @FXML
    private TableColumn<HealthCarePackages, Double> priceColumn;
    @FXML
    private TableColumn<HealthCarePackages, String> descriptionColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<Hospital> hospitalComboBox; // Update to use Hospital objects
    @FXML
    private Button PackageBackButton;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;


    private final HealthCarePackage_Handler packageHandler = new HealthCarePackage_Handler();
    private final Hospital_Handler hospitalHandler = new Hospital_Handler(); // New handler for hospitals

    /**
     * Initialize the UI components and load data.
     */
    @FXML
    public void initialize() {
        // Configure table columns
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        hospitalNameColumn.setCellValueFactory(data -> data.getValue().hospitalNameProperty());
        startDateColumn.setCellValueFactory(data -> data.getValue().startDateProperty());
        endDateColumn.setCellValueFactory(data -> data.getValue().endDateProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        // Load data into the UI components
        loadHealthCarePackages();
        loadHospitalNames();
    }

    /**
     * Load all healthcare packages from the database into the table.
     */
    private void loadHealthCarePackages() {
        List<HealthCarePackages> packageList = packageHandler.getAllPackages();
        packagesTable.getItems().setAll(packageList);
    }

    /**
     * Load all hospital names into the ComboBox.
     */
    private void loadHospitalNames() {
        List<Hospital> hospitals = hospitalHandler.getAllHospitals(); // Fetch all hospitals

        if (!hospitals.isEmpty()) {
            hospitalComboBox.getItems().setAll(hospitals); // Add hospitals to ComboBox
        } else {
            System.out.println("No hospitals found in the database.");
        }
    }

    /**
     * Add a new healthcare package using input fields.
     *
     * @param event The action event.
     */
    public void addPackage(ActionEvent event) {
        try {
            // Collect data from input fields
            String name = nameField.getText();
            Hospital selectedHospital = hospitalComboBox.getValue(); // Get selected hospital
            double price = Double.parseDouble(priceField.getText());
            String description = descriptionField.getText();
            LocalDate startDate = start.getValue(); // Example start date
            LocalDate endDate = end.getValue(); // Example end date
            String priceText = priceField.getText().trim();

            if (startDate != null && startDate.isBefore(LocalDate.now())) {
                showAlert("Validation Error", "Start date cannot be in the past.", Alert.AlertType.WARNING);
                return;
            }
            if (name.isEmpty()) {
                showAlert("Validation Error", "Name cannot be empty.", Alert.AlertType.WARNING);
                return;
            }
            if (description.isEmpty()) {
                showAlert("Validation Error", "Name cannot be empty.", Alert.AlertType.WARNING);
                return;
            }
            if (priceField==null) {
                showAlert("Validation Error", "Price cannot be empty.", Alert.AlertType.WARNING);
                return;
            }

            if (!priceText.matches("\\d+")) { // Matches only digits (whole numbers)
                showAlert("Validation Error", "Price must be a valid number. No letters or symbols allowed.", Alert.AlertType.WARNING);
                return;
            }           // Try to parse the price, if it fails show an erro

            // Validate that the end date is not before the start date
            if (endDate != null && endDate.isBefore(startDate)) {
                showAlert("Validation Error", "End date cannot be before the start date.", Alert.AlertType.WARNING);
                return;
            }
            if (selectedHospital == null) {
                showAlert("Validation Error", "Please select a hospital.", Alert.AlertType.WARNING);
                return;
            }

            // Create a new package object
            HealthCarePackages newPackage = new HealthCarePackages(
                    name,
                    selectedHospital.getName(),
                    startDate,
                    endDate,
                    price,
                    description
            );

            // Add the package to the database using the handler
            boolean isAdded = packageHandler.addPackage(newPackage);

            if (isAdded) {
                // Refresh the table if the package is successfully added
                loadHealthCarePackages();
                clearInputFields();
                showAlert("Success", "Package added successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to add package.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid data for all fields.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Handle the back button click event to navigate to the previous screen.
     *
     * @param actionEvent The action event.
     */
    public void handlePackageBackButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();
            Stage currentStage = (Stage) PackageBackButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin Home Page");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the admin home page.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Clear all input fields.
     */
    private void clearInputFields() {
        nameField.clear();
        priceField.clear();
        descriptionField.clear();
        hospitalComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Show an alert dialog with the specified title and message.
     *
     * @param title   The alert title.
     * @param message The alert message.
     * @param type    The alert type.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
