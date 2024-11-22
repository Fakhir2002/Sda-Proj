package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BookAppointmentController {

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
    }

    // Populate the Hospital ComboBox with the hospital names
    private void populateHospitalComboBox() {

        List<String> hospitals = appointment.getHospitals().stream()
                .map(Hospital::getName)  // Assuming Hospital class has a getName() method
                .collect(Collectors.toList());
        HospitalBox.getItems().addAll(hospitals);

        // Optional: Set default selection
        if (!hospitals.isEmpty()) {
            HospitalBox.setValue(hospitals.get(0));  // Set the first hospital by default
        }
    }

    // Populate the Speciality ComboBox with doctor specialties
    private void populateSpecialityComboBox() {
        List<String> specialities = appointment.getSpecialities();
        SpecialityBox.getItems().addAll(specialities);

        // Optional: Set default selection
        if (!specialities.isEmpty()) {
            SpecialityBox.setValue(specialities.get(0));  // Set the first specialty by default
        }

        // Add a listener to update the Doctor ComboBox when a speciality is selected
        SpecialityBox.setOnAction(event -> populateDoctorComboBox());
    }

    // Populate the Doctor ComboBox based on the selected speciality
    private void populateDoctorComboBox() {
        System.out.println("Doctors available: " + appointment.getDoctors().size());  // Debugging to check if doctors exist

        String selectedSpeciality = SpecialityBox.getValue();  // Get the selected specialty from the dropdown
        System.out.println("Selected Speciality: " + selectedSpeciality);  // Debugging to check the selected specialty

        if (selectedSpeciality != null) {
            // Filter doctors based on the selected specialty
            List<String> filteredDoctors = appointment.getDoctors().stream()
                    .filter(doctor -> selectedSpeciality.equals(doctor.getSpecialty())) // Filter by specialty
                    .map(Doctor::getName)  // Map to doctor names
                    .collect(Collectors.toList());

            DoctorBox.getItems().clear(); // Clear the current items in the combo box
            DoctorBox.getItems().addAll(filteredDoctors); // Add the filtered doctor names

            // Optional: Set default selection
            if (!filteredDoctors.isEmpty()) {
                DoctorBox.setValue(filteredDoctors.get(0));  // Set the first doctor by default
            } else {
                System.out.println("No doctors found for the selected speciality.");  // Debugging if no doctors are found
                DoctorBox.setValue(null); // Clear the selection if no doctors are available
            }
        }
    }



    public void HandleBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) PatientHome.getScene().getWindow();

            // Create a new stage and set it to the scene
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void setPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }
}
