package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminHealthcarePackageController {

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

    // Text fields for user input
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField descriptionField;

    // ComboBox for selecting hospitals
    @FXML
    private ComboBox<String> hospitalComboBox;

    @FXML
    private javafx.scene.control.Button PackageBackButton;

    private List<HealthCarePackages> packageList = new ArrayList<>();

    private Connection connection;

    // Establish database connection
    private Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "username", "12345678");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to connect to the database.");
        }
        return connection;
    }

    // Load healthcare packages from the database into the table
    private void loadHealthCarePackages() {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM healthcare_packages";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                packageList.clear();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String hospitalName = resultSet.getString("hospital_name");
                    LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                    LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    HealthCarePackages packageItem = new HealthCarePackages(name, hospitalName, startDate, endDate, price, description);
                    packageList.add(packageItem);
                }
                packagesTable.getItems().setAll(packageList); // Update the table view
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load hospital names from the database into the ComboBox
    private void loadHospitalNames() {
        try (Connection conn = getConnection()) {
            String query = "SELECT Name FROM hospitals";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                List<String> hospitalNames = new ArrayList<>();
                while (resultSet.next()) {
                    String hospitalName = resultSet.getString("Name");
                    System.out.println("Found hospital: " + hospitalName); // Debug print
                    hospitalNames.add(hospitalName);
                }
                hospitalComboBox.getItems().setAll(hospitalNames);
                System.out.println("ComboBox populated with: " + hospitalNames); // Debug print
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Add new healthcare package to the database
    public void addPackage(ActionEvent event) {
        String name = nameField.getText();
        String hospitalName = hospitalComboBox.getValue(); // Get the selected hospital name
        double price = Double.parseDouble(priceField.getText());
        String description = descriptionField.getText();
        LocalDate startDate = LocalDate.now(); // Example, you can add a date picker for start date
        LocalDate endDate = startDate.plusMonths(1); // Example, add your own logic for end date

        // Insert into database
        try (Connection conn = getConnection()) {
            String insertQuery = "INSERT INTO healthcare_packages (name, hospital_name, start_date, end_date, price, description) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                statement.setString(1, name);
                statement.setString(2, hospitalName); // Use the selected hospital name
                statement.setDate(3, Date.valueOf(startDate));
                statement.setDate(4, Date.valueOf(endDate));
                statement.setDouble(5, price);
                statement.setString(6, description);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Package added successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Refresh table with updated data from database
        loadHealthCarePackages();
    }

    // Event handler for the back button
    public void handlePackageBackButton(ActionEvent actionEvent) {
        try {
            // Load the AdminHome.fxml page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();

            // Get the current stage (window)
            Stage currentStage = (Stage) PackageBackButton.getScene().getWindow();

            // Set the new scene in the current stage
            currentStage.setScene(new Scene(newPage));

            // Set the title for the new scene (optional)
            currentStage.setTitle("Admin Home Page");

            // Resize the window to fit the new scene's content
            currentStage.sizeToScene();

            // Show the window
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any issues loading the FXML file
        }
    }

    // Initialize method to set up the table columns and load hospitals
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        hospitalNameColumn.setCellValueFactory(data -> data.getValue().hospitalNameProperty());
        startDateColumn.setCellValueFactory(data -> data.getValue().startDateProperty());
        endDateColumn.setCellValueFactory(data -> data.getValue().endDateProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        // Load healthcare packages from the database into the table
        loadHealthCarePackages();

        // Load hospital names into the ComboBox
        loadHospitalNames();
    }
}
