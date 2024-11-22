package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HospitalComparisonController {
    @FXML
    private Button backk;
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
}
