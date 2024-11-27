package Controllers;

import OOP.MedicalRecord;
import OOP.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class Patient_ViewMedicalReportController implements InitializeUsername {

    @FXML
    private TableView<MedicalRecord> medicalReportTable;

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
    private Button backButton;

    private Patient currentPatient;
    private ObservableList<MedicalRecord> medicalRecords;

    // Initialize the controller
    public void initialize(String username) {
        currentPatient = new Patient(username);

        symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());
        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
        dateDiagnosedColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        isUpdatedColumn.setCellValueFactory(cellData -> cellData.getValue().isUpdatedProperty());

        loadMedicalReport();
    }

    // Load the medical report for the patient
    private void loadMedicalReport() {
        try {
            List<MedicalRecord> records = MedicalRecord.getReport(currentPatient.getId());
            medicalRecords = FXCollections.observableArrayList(records);
            medicalReportTable.setItems(medicalRecords);
        } catch (Exception e) {
            showAlert("Error", "Unable to load medical report!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Handle the back button action
    @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/PatientHome.fxml"));
            Parent root = loader.load();

            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Show alert method for error handling
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}


