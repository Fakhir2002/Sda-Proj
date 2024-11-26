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

public class Doctor_HomeController {

    public Button appoint;
    public Button VidCon;
    public Button docfaq;
    public Button feed;
    public Button staff;
    public Button LogoutButton;
    public Button Feedback;
    @FXML
    private Label DoctorName;
    private String username;
    @FXML
    private Doctor currentDoctor;

    public void initialize(String username) {
        this.username = username;
        currentDoctor = new Doctor(username);
        DoctorName.setText("Welcome, " + username); // Set the label text to display the username
        System.out.println("Doctor logged in with username: " + currentDoctor.getUsername() +
                " and name: " + currentDoctor.getName());
    }

    private void loadPage(String fxmlFile, String title, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass username to controllers implementing InitializeUsername
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentDoctor.getUsername());
            }

            Stage currentStage = (Stage) sourceButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handleAppointment(ActionEvent actionEvent) {
        loadPage("ManageAppointment.fxml", "Manage Appointment", appoint);
    }

    public void handlevid(ActionEvent actionEvent) {
        loadPage("video-view.fxml", "Video Consultation", VidCon);
    }

    public void handledocfaq(ActionEvent actionEvent) {
        loadPage("DoctorFaq.fxml", "FAQs", docfaq);
    }

    public void handlefeedback(ActionEvent actionEvent) {
        loadPage("DoctorFeedback.fxml", "Doctor Feedback Page", Feedback);
    }

    public void handlestaff(ActionEvent actionEvent) {
        loadPage("Staff-Schedule.fxml", "Staff Scheduling", staff);
    }

    public void HandleLogout(ActionEvent actionEvent) {
        loadPage("HomePage.fxml", "Home Page", LogoutButton);
        System.out.println("Doctor logged out");
    }

    public void Gofeedback(ActionEvent actionEvent) {
        loadPage("DoctorFeedback.fxml", "Doctor Feedback Page", Feedback);
    }
}
