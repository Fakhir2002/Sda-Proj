package Controllers;

import OOP.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_StaffController {

    @FXML
    private TextField usernameField; // Username input field

    @FXML
    private PasswordField passwordField; // Password input field

    @FXML
    private Button stafflogin; // Login button

    @FXML
    private Button hello3; // Back button

    // Handles the "Back" button action
    public void staffgoback(ActionEvent actionEvent) {
        loadPage("/UI/HomePage.fxml", "Home Page", hello3);
    }

    // Handles the "Login" button action
    public void handlestafflogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Please enter both username and password.");
            return;
        }

        if (Staff.validateLogin(username, password)) {
            loadStaffHomePage(username);
        } else {
            // Show an error alert if credentials are invalid
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    // Utility method to load pages
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) currentButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Navigate to the Staff Home Page and pass the staff object
    private void loadStaffHomePage(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/StaffHome.fxml"));
            Parent newPage = loader.load();

            // Pass the staff object to the StaffHomeController
            StaffHomeController controller = loader.getController();
            controller.initialize(username);

            Stage currentStage = (Stage) stafflogin.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Staff Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Utility method to display an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
