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

public class DoctorHomeController
{
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
        this.username=username;
        DoctorName.setText("Welcome, " + username); // Set the label text to display the username
        currentDoctor= new Doctor(username);
        System.out.println("Doctor logged in with username: " + currentDoctor.getUsername() +" and name: "+ currentDoctor.getName());

    }

    public void handleAppointment(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageAppointment.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            ManageAppointmentController controller = loader.getController();

            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) appoint.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Manage Appointment");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handlevid(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("video-view.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) VidCon.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Video Consulttion");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handledocfaq(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorFaq.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            DoctorFaqController controller = loader.getController();

            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) docfaq.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("FAQs");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void handlefeedback(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);




    }

    public void handlestaff(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Staff-Schedule.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) staff.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Staff Scheduling");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void HandleLogout(ActionEvent actionEvent) {
        currentDoctor= new Doctor(username);
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) LogoutButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }

    public void Gofeedback(ActionEvent actionEvent) { try {
        currentDoctor= new Doctor(username);
        // Load the FXML for the About Us application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorFeedback.fxml")); // Ensure AboutUs.fxml exists in the same directory
        Parent newPage = loader.load();

        Stage currentStage = (Stage) Feedback.getScene().getWindow();

        // Create a new stage

        currentStage.setScene(new Scene(newPage));
        currentStage.setTitle("Doctor Feedback Page");
        currentStage.sizeToScene();
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace(); // Debugging in case of issues loading the FXML
    }
    }


}
