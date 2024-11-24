package com.example.proj;

import com.example.temp.DB_HANDLER.Emergency_Handler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AllocateResourcesController extends Application {

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

    private Emergency_Handler emergencyHandler = new Emergency_Handler(); // Emergency_Handler instance

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configure your stage if needed
    }

    public void ConfirmThis(ActionEvent actionEvent) {
        // Your confirmation action code here
    }

    public void GoBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml")); // Ensure StaffHome.fxml exists
            Parent newPage = loader.load();

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
