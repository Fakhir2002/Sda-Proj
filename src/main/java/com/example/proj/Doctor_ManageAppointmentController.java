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

public class Doctor_ManageAppointmentController implements InitializeUsername{

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
        currentDoctor = new Doctor(username);
        appointment = new Appointment();
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

    @FXML
    private Button deleteButton;
    @FXML
    private TextField del;// FXML delete button

    @FXML


    public void display() {


        // Retrieve the doctor ID from the currentDoctor object
        int doctorId = currentDoctor.getId(); // Ensure this method exists in the Doctor class

        // Retrieve the appointments for the logged-in doctor
        ObservableList<Object[]> appointments = appointment.getAppointments(doctorId);

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


            // Update the appointment status to "confirmed"
            appointment.updateAppointmentStatus(appointmentId);

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
    public void handleDeleteAppointment(ActionEvent actionEvent) {
        // Get the appointment ID from the TextField
        String appointmentIdText = del.getText();

        if (appointmentIdText.isEmpty()) {
            // If the input field is empty, show an error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an appointment ID to delete.");
            alert.showAndWait();
            return;
        }

        try {
            int appointmentId = Integer.parseInt(appointmentIdText);


            // Call the delete method
            boolean isDeleted = Appointment.deleteAppointment(appointmentId);

            if (isDeleted) {
                // Refresh the table to reflect the deletion
                display();

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The appointment has been deleted.");
                alert.showAndWait();
            } else {
                // If the appointment was not found or not deleted
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the appointment. Please check the ID and try again.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Handle invalid number format for the appointment ID
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric appointment ID.");
            alert.showAndWait();
        }
    }
}
