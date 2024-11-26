package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Patient_BookAppointmentController implements InitializeUsername {

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
    @FXML
    private Button videobutton;
    @FXML
    private TextField symptomsText;

    private Patient currentPatient;
    private Appointment appointment;
    private MedicalRecord medicalRecord;


    @Override
    public void initialize(String username) {
        currentPatient = new Patient(username);
        System.out.println("Patient logged in: " + currentPatient.getId() + " " + currentPatient.getFirstName());

        appointment = new Appointment();
        medicalRecord = new MedicalRecord();

        // Populate ComboBoxes
        populateHospitalComboBox();
        populateSpecialityComboBox();  // Populate speciality on initialization
        populateDoctorComboBox();      // Populate doctor based on speciality and hospital

        setupComboBoxListeners();

        // Set listener for DatePicker
        datebox.setOnAction(event -> handleDateSelection());
    }

    private void refreshComboBoxes() {
        populateSpecialityComboBox();
        populateDoctorComboBox();
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
        HospitalBox.setOnAction(event -> {
            populateSpecialityComboBox();
            populateDoctorComboBox();
        });

        SpecialityBox.setOnAction(event -> populateDoctorComboBox());
    }

    private void handleDateSelection() {
        if (datebox.getValue() != null) {
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
        if (DoctorBox.getValue() == null || datebox.getValue() == null) {
            return;
        }

        String selectedDoctorName = DoctorBox.getValue();
        String selectedDate = datebox.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int doctorId = appointment.getDoctors().stream()
                .filter(doctor -> doctor.getName().equals(selectedDoctorName))
                .map(Doctor::getId)
                .findFirst()
                .orElse(-1);

        if (doctorId == -1) {
            showAlert("Error", "Selected doctor is invalid. Please try again.");
            return;
        }

        List<String> bookedTimeSlots = Appointment.getBookedTimeSlots(doctorId, selectedDate);
        List<String> allTimeSlots = List.of(
                "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",
                "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM"
        );

        List<String> availableTimeSlots = allTimeSlots.stream()
                .filter(slot -> !bookedTimeSlots.contains(slot))
                .collect(Collectors.toList());

        timeBox.getItems().addAll(availableTimeSlots);

        if (!availableTimeSlots.isEmpty()) {
            timeBox.setValue(availableTimeSlots.get(0));
        } else {
            showAlert("No Slots Available", "There are no available time slots for the selected date and doctor.");
        }
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

        if (Appointment.saveAppointment("Pending", selectedDate, selectedTime, doctorId, patientId)) {
            showAlert("Success", "Appointment confirmed successfully.");


        } else {
            showAlert("Error", "Failed to save the appointment. Please try again.");
        }
        refreshComboBoxes();
    }

    @FXML
    public void handleVideoButton(ActionEvent event) {
        if (datebox.getValue() != null || timeBox.getValue() != null) {
            showAlert("Error", "Date and time must be empty for video consultation.");
            clearFields();
            return;
        }

        String symptoms = symptomsText.getText();
        if (symptoms.isBlank()) {
            showAlert("Error", "Please provide your symptoms for a video consultation request.");
            return;
        }

        String selectedDoctorName = DoctorBox.getValue();
        Doctor selectedDoctor = new Doctor();
        int doctorId = selectedDoctor.getDoctorIdByName(selectedDoctorName);

        // Check if the patient has already requested a video consultation with the selected doctor
        if (medicalRecord.hasExistingVideoConsultation(currentPatient.getId(), doctorId)) {
            showAlert("Consultation Already Requested", "You have already requested a video consultation with this doctor.");
            return;
        }

        // If no existing consultation, proceed with saving the new video consultation
        if (medicalRecord.saveMedicalHistory(symptoms, currentPatient.getId(), doctorId)) {
            showAlert("Success", "Video consultation requested successfully.");
        } else {
            showAlert("Error", "Failed to request video consultation. Please try again.");
        }
        refreshComboBoxes();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
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

    private void clearFields() {
        datebox.setValue(null);
        HospitalBox.setValue(null);
        SpecialityBox.setValue(null);
        DoctorBox.setValue(null);
        timeBox.setValue(null);
    }
}
