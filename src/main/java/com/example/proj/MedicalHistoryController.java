package com.example.proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MedicalHistoryController {

    @FXML
    private TableView<MedicalRecord> medicalHistoryTable;  // TableView for displaying medical records

    @FXML
    private TableColumn<MedicalRecord, String> diagnosisColumn;  // Column for diagnosis
    @FXML
    private TableColumn<MedicalRecord, String> treatmentColumn;  // Column for treatment
    @FXML
    private TableColumn<MedicalRecord, String> dateDiagnosedColumn;  // Column for date of diagnosis

    @FXML
    private Button updateButton;  // Update button to update selected medical record
    @FXML
    private Button nikal;    // Back button to go to previous page

    @FXML
    private TextArea diagnosisTextArea;  // TextArea for entering diagnosis
    @FXML
    private TextArea treatmentTextArea;  // TextArea for entering treatment

    @FXML
    private Label diagnosisLabel;  // Label for diagnosis
    @FXML
    private Label treatmentLabel;  // Label for treatment

    private ObservableList<MedicalRecord> medicalRecords;  // List to hold medical records

    public void handleBack(ActionEvent event) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent root = loader.load();
           // StaffHomeController controller = loader.getController();
           //  controller.initialize(currentStaff.getUsername());
            // Set up the scene and stage
            Stage currentStage = (Stage) nikal.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

