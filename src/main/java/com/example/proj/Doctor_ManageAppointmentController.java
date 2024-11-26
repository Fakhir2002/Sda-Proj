package com.example.proj;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Doctor_ManageAppointmentController implements InitializeUsername {

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

    @FXML
    private Doctor currentDoctor;
    @FXML
    private Appointment appointment;

    // This method is called when the FXML page is loaded
    @FXML
    public void initialize(String username) {
        currentDoctor = new Doctor(username);  // Initialize current doctor
        appointment = new Appointment();  // Initialize appointment object
        display();  // Call display method to show appointments
    }

    // Handle the back button click to navigate back to the Doctor's home page
    public void handlebackfromApp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();

            Doctor_HomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) backfromapp.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Display all appointments for the current doctor
    public void display() {
        int doctorId = currentDoctor.getId(); // Retrieve doctor ID from currentDoctor object
        ObservableList<Object[]> appointments = appointment.getAppointments(doctorId); // Fetch appointments for the doctor
        appointmentTable.setItems(appointments); // Set the table items

        // Set the cell value factories to display the data in the table
        ID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty((Integer) cellData.getValue()[0]).asObject());
        Name.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        Date.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[2]));
        time.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[3]));
        status.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[4]));
    }

    // Handle the confirmation of an appointment
    @FXML
    public void HandleConfirm(ActionEvent actionEvent) {
        // Get the selected appointment from the TableView
        Object[] selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            int appointmentId = (Integer) selectedAppointment[0];  // Get the appointment ID

            // Update the appointment status to "confirmed"
            boolean isUpdated = appointment.updateAppointmentStatus(appointmentId);
            if (isUpdated) {
                // Prepare the notification message
                String notificationMessage = "Your appointment with Dr. " + currentDoctor.getName() + " is confirmed. " +
                        "Please make sure to be prepared for the consultation.";

                // Create and save the notification
                Notification notification = NotificationFactory.createNotification(0, notificationMessage, false,
                        appointment.getPatientIdByAppointmentId(appointmentId), null, null);
                boolean isNotificationSaved = notification.saveNotification();

                if (isNotificationSaved) {
                    // Log and refresh the table after the notification is saved
                    System.out.println("Notification sent to patient: " + notificationMessage);
                    display();  // Refresh the appointment table

                    // Show confirmation message to the doctor
                    showAlert(Alert.AlertType.INFORMATION, "Appointment Status", null, "The appointment has been confirmed.");
                } else {
                    // Show error if notification failed
                    showAlert(Alert.AlertType.ERROR, "Error", "Notification Failed", "Failed to send notification to the patient.");
                }
            } else {
                // Show error if appointment status update failed
                showAlert(Alert.AlertType.ERROR, "Error", "Appointment Update Failed", "Failed to confirm the appointment.");
            }

        } else {
            // If no appointment is selected
            showAlert(Alert.AlertType.WARNING, "No Appointment Selected", null, "Please select an appointment to confirm.");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
