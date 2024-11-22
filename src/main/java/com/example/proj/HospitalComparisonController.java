package com.example.proj;

import com.example.temp.DB_HANDLER.Hospital_Handler;
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

public class HospitalComparisonController {

    @FXML
    private Button backk;

    @FXML
    private ComboBox<String> hospitalComboBox1;

    @FXML
    private ComboBox<String> hospitalComboBox2;

    private Hospital_Handler hospitalHandler;

    public void initialize() {
        // Initialize the hospital handler
        hospitalHandler = new Hospital_Handler();

        // Fetch all hospital names from the database
        List<String> hospitalNames = hospitalHandler.getHospitalNames();

        // Add hospital names to both ComboBoxes
        hospitalComboBox1.getItems().addAll(hospitalNames);
        hospitalComboBox2.getItems().addAll(hospitalNames);
    }

    public void backfromhos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Comparison.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) backk.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Comparison");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compareHospitals(ActionEvent actionEvent) {
        String hospitalName1 = hospitalComboBox1.getValue();
        String hospitalName2 = hospitalComboBox2.getValue();

        if (hospitalName1 != null && hospitalName2 != null) {
            // Initialize the comparison logic
            HospitalComparison comparison = new HospitalComparison();
            String result = comparison.compareHospitals(hospitalName1, hospitalName2);

            // Show the result (you can display it in a label or dialog)
            System.out.println(result);  // Example: Print the result to the console
        } else {
            System.out.println("Please select both hospitals.");
        }
    }
}
