package com.example.proj;

import com.example.temp.DB_HANDLER.Appointment_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BookAppointmentController {

    @FXML
    private Button Confirm;
    @FXML
    private Button PatientHome;

    @FXML
    private ComboBox<String> HospitalBox;

    @FXML
    private ComboBox<String> SpecialityBox;

    @FXML
    private ComboBox<String> DoctorBox;

    @FXML
    private ComboBox<String> timeBox;

    @FXML
    private DatePicker datebox;

    private Patient currentPatient;
    private Appointment appointment;

    public void initialize(String username) {
        // Create Patient object
        currentPatient = new Patient(username);
        System.out.println("Patient Object in Book Appointments with username: " + currentPatient.getUsername());

        // Initialize the Appointment object and populate the ComboBoxes
        appointment = new Appointment();
        populateHospitalComboBox();  // Populate Hospital ComboBox first
        populateSpecialityComboBox();  // Populate Speciality ComboBox

        // Attach listener to DatePicker for populating timeBox
        datebox.setOnAction(event -> handleDateSelection());
    }

    private void populateHospitalComboBox() {
        List<String> hospitals = appointment.getHospitals().stream()
                .map(Hospital::getName)  // Assuming Hospital class has a getName() method
                .collect(Collectors.toList());
        HospitalBox.getItems().addAll(hospitals);

        if (!hospitals.isEmpty()) {
            HospitalBox.setValue(hospitals.get(0));
        }
    }

    private void populateSpecialityComboBox() {
        List<String> specialities = appointment.getSpecialities();
        SpecialityBox.getItems().addAll(specialities);

        if (!specialities.isEmpty()) {
            SpecialityBox.setValue(specialities.get(0));
        }

        SpecialityBox.setOnAction(event -> populateDoctorComboBox());
    }

    private void populateDoctorComboBox() {
        String selectedSpeciality = SpecialityBox.getValue();
        if (selectedSpeciality != null) {
            List<String> filteredDoctors = appointment.getDoctors().stream()
                    .filter(doctor -> selectedSpeciality.equals(doctor.getSpecialty()))
                    .map(Doctor::getName)
                    .collect(Collectors.toList());

            DoctorBox.getItems().clear();
            DoctorBox.getItems().addAll(filteredDoctors);

            if (!filteredDoctors.isEmpty()) {
                DoctorBox.setValue(filteredDoctors.get(0));
            } else {
                DoctorBox.setValue(null);
            }
        }
    }

    public void HandleBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            // Get the controller for the PatientHome screen
            PatientHomeController controller = loader.getController();

            // Pass the username to the PatientHomeController
            controller.initialize(currentPatient.getUsername());

            Stage currentStage = (Stage) PatientHome.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }

    private void handleDateSelection() {
        if (datebox.getValue() != null) {
            System.out.println("Selected Date: " + datebox.getValue());

            // Validate if the selected date is in the past
            if (datebox.getValue().isBefore(java.time.LocalDate.now())) {
                showAlert("Invalid Date", "You cannot select a date that has already passed.");
                datebox.setValue(java.time.LocalDate.now());  // Reset to current date
                return;
            }

            // Proceed to populate time slots if the date is valid
            populateTimeBox();
        }
    }

    private void populateTimeBox() {
        timeBox.getItems().clear(); // Clear any existing items in the ComboBox

        // Define morning and evening time slots
        List<String> timeSlots = List.of(
                "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",  // Morning slots
                "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM"   // Evening slots
        );

        timeBox.getItems().addAll(timeSlots);

        if (!timeSlots.isEmpty()) {
            timeBox.setValue(timeSlots.get(0)); // Set the first slot by default
        }
        System.out.println("TimeBox populated with: " + timeSlots); // Debugging to ensure the ComboBox is populated
    }





    @FXML
    public void handleConfirm(ActionEvent event) {
        // Validate input fields
        if (DoctorBox.getValue() == null || timeBox.getValue() == null || datebox.getValue() == null) {
            showAlert("Error", "Please fill all fields before confirming the appointment.");
            return;
        }

        // Get selected values
        String selectedDate = datebox.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String selectedTime = timeBox.getValue();
        String selectedDoctorName = DoctorBox.getValue();

        // Lookup doctor_id using the selected doctor's name
        int doctorId = appointment.getDoctors().stream()
                .filter(doctor -> doctor.getName().equals(selectedDoctorName))
                .map(Doctor::getId) // Assuming Doctor class has a getId() method
                .findFirst()
                .orElse(-1);

        if (doctorId == -1) {
            showAlert("Error", "Selected doctor is invalid. Please try again.");
            return;
        }

        // Get patient ID
        int patientId = currentPatient.getId(); // Assuming Patient class has a getId() method

        // Save appointment to DB
        Appointment_Handler appointmentHandler = new Appointment_Handler();
        boolean isSaved = appointmentHandler.saveAppointment("Pending", selectedDate, selectedTime, doctorId, patientId);

        if (isSaved) {
            showAlert("Success", "Appointment confirmed successfully.");
        } else {
            showAlert("Error", "Failed to save the appointment. Please try again.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



