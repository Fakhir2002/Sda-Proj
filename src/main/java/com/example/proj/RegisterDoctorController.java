package com.example.proj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegisterDoctorController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
