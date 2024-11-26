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

public class Doctor_StaffScheduleController implements InitializeUsername {

    @FXML
    private Label welcomeText;
    @FXML
    private Button backfromss;
    @FXML
    private Button rescheduleButton;
    @FXML
    private TableView<Object[]> appointmentTable;
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
    private Doctor currentDoctor;

    // Initialize the controller with the doctor's username
    @FXML
    public void initialize(String username) {
        currentDoctor = new Doctor(username);
        displayPendingAppointments();
    }

    // Populate the table with pending appointments for the current doctor
    public void displayPendingAppointments() {
        // Retrieve the doctor's ID
        int doctorId = currentDoctor.getId(); // Ensure this method exists in the Doctor class

        // Retrieve pending appointments for the doctor
        ObservableList<Object[]> pendingAppointments = new Appointment().getPendingAppointments(doctorId);

        // Set items in the table
        appointmentTable.setItems(pendingAppointments);

        // Set cell value factories for the columns
        ID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty((Integer) cellData.getValue()[0]).asObject());
        status.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        Date.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[2]));
        time.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[3]));
        Name.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("Patient ID: " + cellData.getValue()[4])); // Display patient ID as placeholder for Name
    }


    // Handle the "Back" button click, navigating back to the Doctor's home page
    public void handlebackfromss(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();

            Doctor_HomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) backfromss.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle the "Reschedule" button click to delete an appointment and notify the patient

    @FXML
    public void HandleConfirm(ActionEvent actionEvent) {
        // Get the selected appointment from the table
        Object[] selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            int appointmentId = (Integer) selectedAppointment[0]; // Get the appointment ID
            int patientId = (Integer) selectedAppointment[4]; // Get the patient ID from the selected row

            // Call the method to delete the appointment from the database
            boolean isDeleted = Appointment.deleteAppointment(appointmentId);

            if (isDeleted) {
                // Get the doctor's name
                String doctorName = currentDoctor.getName(); // Ensure getName() exists in Doctor class

                // Create a new notification with the doctor's name included in the description
                String description = "Your appointment with Dr. " + doctorName +
                        " has been cancelled due to the unavailability of the doctor. Kindly reschedule.";

                Notification notification = NotificationFactory.createNotification(0, description, false, patientId, null, null);

                // Save the notification to the database
                boolean isNotificationSaved = notification.saveNotification();


                if (isNotificationSaved) {
                    // Show confirmation alert that the appointment was deleted and the patient was notified
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Rescheduled");
                    alert.setHeaderText(null);
                    alert.setContentText("The appointment has been deleted, and a notification has been sent to the patient.");
                    alert.showAndWait();

                    // Refresh the table to show the updated list of pending appointments
                    displayPendingAppointments();
                } else {
                    // Show an error alert if the notification could not be saved
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Notification Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to send the notification. Please try again.");
                    alert.showAndWait();
                }

            } else {
                // Show an error alert if the appointment could not be deleted
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Reschedule Failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to reschedule the appointment. Please try again.");
                alert.showAndWait();
            }
        } else {
            // If no appointment is selected, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an appointment to reschedule.");
            alert.showAndWait();
        }
    }


}
