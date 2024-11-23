package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DoctorComparisonController {

    @FXML
    private ComboBox<String> doctorComboBox1; // ComboBox for Doctor 1
    @FXML
    private ComboBox<String> doctorComboBox2; // ComboBox for Doctor 2

    @FXML
    private TableView<DoctorComparison> doctorTableView; // TableView for comparison
    @FXML
    private TableColumn<DoctorComparison, String> attributeColumn; // Attribute column
    @FXML
    private TableColumn<DoctorComparison, String> doctor1Column; // Doctor 1 column
    @FXML
    private TableColumn<DoctorComparison, String> doctor2Column; // Doctor 2 column

    private final Doctor_Handler doctorHandler = new Doctor_Handler(); // Assuming Doctor_Handler handles DB access

    public void initialize() {
        // Populate ComboBoxes
        loadDoctorNames();

        // Initialize TableView columns
        attributeColumn.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        doctor1Column.setCellValueFactory(new PropertyValueFactory<>("doctor1Value"));
        doctor2Column.setCellValueFactory(new PropertyValueFactory<>("doctor2Value"));
    }

    private void loadDoctorNames() {
        List<String> doctorNames = doctorHandler.getAllDoctors(); // Fetch doctor names from the database
        if (doctorNames != null) {
            doctorComboBox1.getItems().addAll(doctorNames);
            doctorComboBox2.getItems().addAll(doctorNames);
        }
    }

    @FXML
    public void aageychalo(ActionEvent actionEvent) {
        try {
            // Get selected doctors
            String doctor1Name = doctorComboBox1.getValue();
            String doctor2Name = doctorComboBox2.getValue();

            if (doctor1Name == null || doctor2Name == null) {
                System.out.println("Please select two doctors for comparison.");
                return;
            }

            // Fetch doctor details
            Doctor doctor1 = Doctor.getDoctorbyName(doctor1Name);
            Doctor doctor2 = Doctor.getDoctorbyName(doctor2Name);

            // Clear TableView and populate it with comparison data
            doctorTableView.getItems().clear();
            doctorTableView.getItems().addAll(
                    new DoctorComparison("Name", doctor1.getName(), doctor2.getName()),
                    new DoctorComparison("Specialty", doctor1.getSpecialty(), doctor2.getSpecialty()),
                    new DoctorComparison("Hospital", doctor1.getHospital(), doctor2.getHospital()),
                    new DoctorComparison("DOB", doctor1.getDob(), doctor2.getDob()),
                    new DoctorComparison("Contact", doctor1.getContact(), doctor2.getContact()),
                    new DoctorComparison("Address", doctor1.getAddress(), doctor2.getAddress())
            );

            // Optionally exclude sensitive data like passwordHash

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void backfromdoccomp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Comparison.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) doctorTableView.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Comparison");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
