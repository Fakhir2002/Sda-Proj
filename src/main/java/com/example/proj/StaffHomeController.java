package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffHomeController implements InitializeUsername {

    @FXML
    private Button InventoryButton;
    @FXML
    private Button ResourcesButton;
    @FXML
    private Button aboutSyntegrityButton;
    @FXML
    private Button RoomAllocationButton;
    @FXML
    private Button StaffLogoutButton;
    @FXML
    private Label staffName;
    @FXML
    private String username;
    @FXML
    private Staff currentStaff;

    @Override
    public void initialize(String username) {
        this.username = username;
        currentStaff = new Staff(username);

        staffName.setText("Welcome, " + username);
        System.out.println("Staff logged in with username: " + currentStaff.getUsername() + " and name: " + currentStaff.getFirstName());
    }

    // General method to load pages for different buttons
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass the username to the controllers where necessary
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentStaff.getUsername());
            }

            Stage currentStage = (Stage) currentButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Event handlers for different buttons
    public void HandleManageInventory(ActionEvent actionEvent) {
        loadPage("Manage-Inventory.fxml", "Manage Inventory", InventoryButton);
    }
    public void AllocateResourceButton(ActionEvent actionEvent) {
        loadPage("AllocateResources.fxml", "Allocate Resources", ResourcesButton);
    }

    public void HandleStaffLogout(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            System.out.println("Staff logged out");

            Stage currentStage = (Stage) StaffLogoutButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }


}
