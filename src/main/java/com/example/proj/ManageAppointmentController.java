package com.example.proj;

import com.example.temp.DB_HANDLER.ManageAppointment_Handler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageAppointmentController {

    @FXML
    private TableColumn<Object[], Integer> ID;
    @FXML
    private TableColumn<Object[], String> Name;
    @FXML
    private TableColumn<Object[], String> Date;
    @FXML
    private TableColumn<Object[], String> time;
    @FXML
    private TableColumn<Object[], String> status;

    @FXML
    private TableView<Object[]> appointmentTable;

    @FXML
    private Button Confirm;
    @FXML
    private Label welcomeText;
    public Button backfromapp;

    // This method is called when the FXML page is loaded
    @FXML
    public void initialize() {
        // Call the method to fetch and display appointments as soon as the page is loaded
        display();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void handlebackfromApp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();
            Stage currentStage = (Stage) backfromapp.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        // Create the handler instance
        ManageAppointment_Handler handler = new ManageAppointment_Handler();

        // Retrieve the appointments from the database
        ObservableList<Object[]> appointments = handler.getAppointments();

        // Set the items in the table
        appointmentTable.setItems(appointments);

        // Set cell value factories to access the data in the Object array
        ID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty((Integer) cellData.getValue()[0]).asObject());
        Name.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        Date.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[2]));
        time.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[3]));
        status.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[4]));
    }

    @FXML
    public void HandleConfirm(ActionEvent actionEvent) {
        // Get the selected appointment from the TableView
        Object[] selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            // Get the appointment ID from the selected row
            int appointmentId = (Integer) selectedAppointment[0];

            // Create the handler instance to update the status
            ManageAppointment_Handler handler = new ManageAppointment_Handler();

            // Update the appointment status to "confirmed"
            handler.updateAppointmentStatus(appointmentId);

            // Refresh the table to show the updated status
            display();

            // Show confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Status");
            alert.setHeaderText(null);
            alert.setContentText("The appointment has been confirmed.");
            alert.showAndWait();
        } else {
            // If no appointment is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an appointment to confirm.");
            alert.showAndWait();
        }
    }
}
