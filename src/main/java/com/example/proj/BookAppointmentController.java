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
        System.out.println("Patient Object in Book Appointments with username: " + currentPatient.getId() + " " + currentPatient.getFirstName());

        // Initialize the Appointment object
        appointment = new Appointment();

        // Populate Hospital ComboBox
        populateHospitalComboBox();

        // Add listeners to refresh dependent ComboBoxes
        setupComboBoxListeners();

        // Attach listener to DatePicker
        datebox.setOnAction(event -> handleDateSelection());
    }

    private void populateHospitalComboBox() {
        List<String> hospitals = appointment.getHospitals().stream()
                .map(Hospital::getName)
                .collect(Collectors.toList());
        HospitalBox.getItems().addAll(hospitals);

        if (!hospitals.isEmpty()) {
            HospitalBox.setValue(hospitals.get(0));
        }
    }

    private void populateSpecialityComboBox() {
        SpecialityBox.getItems().clear();
        List<String> specialities = appointment.getSpecialities();
        SpecialityBox.getItems().addAll(specialities);

        if (!specialities.isEmpty()) {
            SpecialityBox.setValue(specialities.get(0));
        }
    }

    private void populateDoctorComboBox() {
        DoctorBox.getItems().clear();

        String selectedSpeciality = SpecialityBox.getValue();
        String selectedHospital = HospitalBox.getValue();

        if (selectedSpeciality != null && selectedHospital != null) {
            List<String> filteredDoctors = appointment.getDoctors().stream()
                    .filter(doctor -> selectedSpeciality.equals(doctor.getSpecialty())
                            && selectedHospital.equals(doctor.getHospital()))
                    .map(Doctor::getName)
                    .collect(Collectors.toList());

            DoctorBox.getItems().addAll(filteredDoctors);

            if (!filteredDoctors.isEmpty()) {
                DoctorBox.setValue(filteredDoctors.get(0));
            }
        }
    }

    private void setupComboBoxListeners() {
        // HospitalBox Listener
        HospitalBox.setOnAction(event -> {
            populateSpecialityComboBox();
            populateDoctorComboBox(); // Refresh doctor list based on hospital selection
        });

        // SpecialityBox Listener
        SpecialityBox.setOnAction(event -> populateDoctorComboBox());
    }

    private void handleDateSelection() {
        if (datebox.getValue() != null) {
            System.out.println("Selected Date: " + datebox.getValue());

            if (datebox.getValue().isBefore(java.time.LocalDate.now())) {
                showAlert("Invalid Date", "You cannot select a date that has already passed.");
                datebox.setValue(java.time.LocalDate.now());
                return;
            }

            populateTimeBox();
        }
    }

    private void populateTimeBox() {
        timeBox.getItems().clear();

        List<String> timeSlots = List.of(
                "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",
                "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM"
        );

        timeBox.getItems().addAll(timeSlots);

        if (!timeSlots.isEmpty()) {
            timeBox.setValue(timeSlots.get(0));
        }

        System.out.println("TimeBox populated with: " + timeSlots);
    }

    @FXML
    public void handleConfirm(ActionEvent event) {
        if (DoctorBox.getValue() == null || timeBox.getValue() == null || datebox.getValue() == null) {
            showAlert("Error", "Please fill all fields before confirming the appointment.");
            return;
        }

        String selectedDate = datebox.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String selectedTime = timeBox.getValue();
        String selectedDoctorName = DoctorBox.getValue();

        int doctorId = appointment.getDoctors().stream()
                .filter(doctor -> doctor.getName().equals(selectedDoctorName))
                .map(Doctor::getId)
                .findFirst()
                .orElse(-1);

        if (doctorId == -1) {
            showAlert("Error", "Selected doctor is invalid. Please try again.");
            return;
        }

        int patientId = currentPatient.getId();

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

    public void HandleBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();
            PatientHomeController controller = loader.getController();
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
}
