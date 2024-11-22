package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class DoctorComparisonController {

    @FXML
    private ComboBox<String> doctorComboBox1;  // ComboBox for Doctor 1
    @FXML
    private ComboBox<String> doctorComboBox2;  // ComboBox for Doctor 2
    @FXML
    private Button backyy;

    private Doctor_Handler doctorHandler = new Doctor_Handler(); // Assuming Doctor_Handler handles DB access

    public void initialize() {
        // Populate the ComboBoxes with doctor names from the database
        loadDoctorNames();
    }

    private void loadDoctorNames() {
        List<String> doctorNames = doctorHandler.getAllDoctors(); // Fetch doctor names from DB
        if (doctorNames != null) {
            doctorComboBox1.getItems().addAll(doctorNames);
            doctorComboBox2.getItems().addAll(doctorNames);
        }
    }

    // Original function for handling the "Back" button action
    @FXML
    public void backfromdoccomp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Comparison.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) backyy.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Comparison");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional: You can add more comparison functionality or additional event handlers here
}
