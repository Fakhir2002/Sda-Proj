package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VideoController implements InitializeUsername {

    @FXML
    private Button ConsultButton;
    @FXML
    private ComboBox<String> PatientComboBox; // Change type to String for patient names
    @FXML
    private Button backfromvid;

    @FXML
    private Doctor currentDoctor;
    @FXML
    private VideoConsultation videoConsultation;
    @FXML
    private Patient patient;  // Assuming you have a Patient_Handler instance

    @FXML
    public void initialize(String username) {
        currentDoctor = new Doctor(username);
        videoConsultation = new VideoConsultation();
        patient = new Patient();  // Initialize Patient_Handler

        // Populate the ComboBox with patient names for the current doctor
        populatePatientComboBox(currentDoctor.getId());
    }

    private void populatePatientComboBox(int doctorId) {
        // Fetch the list of patient IDs for the current doctor
        List<Integer> patientIds = videoConsultation.getConsultationsForDoctor(doctorId);

        // Clear any existing items in the ComboBox
        PatientComboBox.getItems().clear();

        // Add all patient names to the ComboBox
        for (int patientId : patientIds) {
            // Get the patient name by ID
            String patientName = patient.getPatientNameById(patientId);

            // Add the patient name to the ComboBox
            if (patientName != null) {
                PatientComboBox.getItems().add(patientName);
            }
        }
    }

    public void handlebackvid(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Doctor's home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();

            DoctorHomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) backfromvid.getScene().getWindow();

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handlerequests(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Doctor's home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MedicalHistory.fxml"));
            Parent newPage = loader.load();

            //DoctorHomeController controller = loader.getController();
            //controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) ConsultButton.getScene().getWindow();

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
