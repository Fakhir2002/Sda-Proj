package Controllers;

import OOP.*;
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

public class Doctor_MedicalHistoryController {

    @FXML
    private TableView<MedicalRecord> medicalHistoryTable;

    @FXML
    private TableColumn<MedicalRecord, String> symptomsColumn;

    @FXML
    private TableColumn<MedicalRecord, String> diagnosisColumn;

    @FXML
    private TableColumn<MedicalRecord, String> treatmentColumn;

    @FXML
    private TableColumn<MedicalRecord, String> dateDiagnosedColumn;

    @FXML
    private TableColumn<MedicalRecord, Boolean> isUpdatedColumn;

    @FXML
    private TextArea symptomsTextArea;

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
        medicalRecord = new MedicalRecord();

        symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());
        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
        dateDiagnosedColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        isUpdatedColumn.setCellValueFactory(cellData -> cellData.getValue().isUpdatedProperty());

        loadMedicalHistory();

        // Disable the update button by default
        updateButton.setDisable(true);

        // Add a listener to enable updates only for records without diagnosis and treatment
        medicalHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                boolean isEmpty = newSelection.getDiagnosis() == null || newSelection.getDiagnosis().isEmpty();
                boolean hasNoTreatment = newSelection.getTreatment() == null || newSelection.getTreatment().isEmpty();
                updateButton.setDisable(!(isEmpty && hasNoTreatment));
                if (isEmpty && hasNoTreatment) {
                    diagnosisTextArea.clear();
                    treatmentTextArea.clear();
                }
            }
        });
    }

    private void loadMedicalHistory() {
        List<MedicalRecord> records = medicalRecord.getMedicalHistory(currentPatient.getId());
        medicalRecords = FXCollections.observableArrayList(records);
        medicalHistoryTable.setItems(medicalRecords);
    }


    @FXML
    private void handleUpdate(ActionEvent event) {
        MedicalRecord selectedRecord = medicalHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("Error", "Please select a record to update!", Alert.AlertType.ERROR);
            return;
        }

        String diagnosis = diagnosisTextArea.getText();
        String treatment = treatmentTextArea.getText();

        if (diagnosis.isEmpty() || treatment.isEmpty()) {
            showAlert("Error", "Diagnosis and Treatment fields cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        String currentDate = java.time.LocalDate.now().toString();

        // Update the medical record
        boolean success = medicalRecord.updateMedicalHistory(
                selectedRecord.getHistoryID(),
                diagnosis,
                treatment,
                currentDate
        );

        if (success) {
            // Create the notification message for the patient
            String notificationMessage = "Video consultation with Dr. " + currentDoctor.getName() + " concluded. Symptoms: " + selectedRecord.getSymptoms() +
                    ", Diagnosis: " + diagnosis + ", Treatment: " + treatment + ".\n\n" +
                    "Kindly pay your bill for the consultation at your earliest convenience.";


            // Create and save the notification
            Notification notification = NotificationFactory.createNotification(0, notificationMessage, false, currentPatient.getId(), null, null);
            boolean isNotificationSaved = notification.saveNotification();

            // Log the notification sent to the patient (optional)
            System.out.println("Notification sent to patient: " + notificationMessage);

            // Create a payment of 5000 for the consultation
            double consultationFee = 5000;
            String paymentDescription = "Medical Consultation Fee for Video Consultation with Dr. " + currentDoctor.getName();
            String paymentStatus = "Unpaid"; // Initially marked as unpaid

            // Create a new payment object (assuming a Payment class exists)
            Bill payment = new Bill();
            boolean paymentCreated = payment.addPayment(currentPatient.getId(),paymentDescription, consultationFee, paymentStatus);

            if (paymentCreated) {
                System.out.println("Payment of " + consultationFee + " created for the consultation.");
            } else {
                System.out.println("Failed to create payment.");
            }

            // Show success alert
            showAlert("Success", "Medical record updated and payment created successfully!", Alert.AlertType.INFORMATION);

            // Reload the medical history table
            loadMedicalHistory();
            diagnosisTextArea.clear();
            treatmentTextArea.clear();
        } else {
            showAlert("Error", "Failed to update medical record.", Alert.AlertType.ERROR);
        }
    }



    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/DoctorHome.fxml"));
            Parent root = loader.load();

            Doctor_HomeController controller = loader.getController();
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
