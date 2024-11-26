package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Staff_ManageInventoryController implements InitializeUsername{
    @FXML
    private Label welcomeText;
    @FXML
    private Button InventoryButton;
    @FXML
    private Button saveee;
    @FXML
    private Slider medSlider;
    @FXML
    private Slider stockSlider;
    @FXML
    private Slider miscSlider;
    @FXML
    private TextField medTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextField miscTextField;

    @FXML
    public void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private Staff currentStaff;

    @Override
    public void initialize(String username) {
        currentStaff = new Staff(username);

        // Set default values for the sliders
        medSlider.setValue(100);
        stockSlider.setValue(100);
        miscSlider.setValue(100);

        // Update the text fields when the slider value changes
        medSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                medTextField.setText(String.format("%.0f", newValue))
        );
        stockSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                stockTextField.setText(String.format("%.0f", newValue))
        );
        miscSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                miscTextField.setText(String.format("%.0f", newValue))
        );

        // Update slider when the text field value changes manually
        medTextField.textProperty().addListener((observable, oldText, newText) -> {
            try {
                medSlider.setValue(Integer.parseInt(newText));
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., empty text or non-numeric)
            }
        });

        stockTextField.textProperty().addListener((observable, oldText, newText) -> {
            try {
                stockSlider.setValue(Integer.parseInt(newText));
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., empty text or non-numeric)
            }
        });

        miscTextField.textProperty().addListener((observable, oldText, newText) -> {
            try {
                miscSlider.setValue(Integer.parseInt(newText));
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., empty text or non-numeric)
            }
        });
    }

    @FXML
    public void handleSaveButton(ActionEvent actionEvent) {
        // Get the updated values from the sliders (or text fields)
        int medStock = (int) medSlider.getValue();
        int stockStock = (int) stockSlider.getValue();
        int miscStock = (int) miscSlider.getValue();

       String hospital= currentStaff.getHospital();

        // Update the database
        Inventory.addInventory(hospital,medStock, stockStock, miscStock);

        // Optionally, show a confirmation message in the console
        System.out.println("Inventory updated successfully!");

        // Show an alert to the user confirming the inventory update
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inventory Updated");
        alert.setHeaderText(null);
        alert.setContentText("Inventory has been successfully updated!");
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    @FXML
    public void Handleback(ActionEvent actionEvent) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml"));
            Parent root = loader.load();
            StaffHomeController controller = loader.getController();
            controller.initialize(currentStaff.getUsername());


            // Set up the scene and stage
            Stage currentStage = (Stage) InventoryButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
