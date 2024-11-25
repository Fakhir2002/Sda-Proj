package com.example.proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MedicalHistoryController {

    @FXML
    private TableView<MedicalRecord> medicalHistoryTable;

    @FXML
    private TableColumn<MedicalRecord, String> diagnosisColumn;

    @FXML
    private TableColumn<MedicalRecord, String> treatmentColumn;

    @FXML
    private TableColumn<MedicalRecord, String> dateDiagnosedColumn;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextArea treatmentTextArea;

    @FXML
    private Button updateButton;

    @FXML
    private Button nikal;

    private ObservableList<MedicalRecord> medicalRecords;

    private Patient currentPatient;
    private Doctor currentDoctor;
    private MedicalRecord medicalRecord;


    public void initialize(String patientUsername, String doctorUsername) {
        currentPatient = new Patient(patientUsername);
        currentDoctor = new Doctor(doctorUsername);

        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
        dateDiagnosedColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        loadMedicalHistory();
    }

    private void loadMedicalHistory() {
        List<MedicalRecord> records = medicalRecord.getMedicalHistory(currentPatient.getId());
        medicalRecords = FXCollections.observableArrayList(records);
        medicalHistoryTable.setItems(medicalRecords);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String diagnosis = diagnosisTextArea.getText();
        String treatment = treatmentTextArea.getText();
        String currentDate = java.time.LocalDate.now().toString();

        if (diagnosis.isEmpty() || treatment.isEmpty()) {
            showAlert("Error", "Fields cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        boolean success = medicalRecord.saveMedicalHistory(diagnosis, treatment, currentDate, currentPatient.getId());
        if (success) {
            showAlert("Success", "Medical record saved successfully!", Alert.AlertType.INFORMATION);
            loadMedicalHistory();
            diagnosisTextArea.clear();
            treatmentTextArea.clear();
        } else {
            showAlert("Error", "Failed to save medical record.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent root = loader.load();

            DoctorHomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) nikal.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
