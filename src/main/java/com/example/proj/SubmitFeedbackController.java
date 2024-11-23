package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;
import com.example.temp.DB_HANDLER.Feedback_Handler;
import com.example.temp.DB_HANDLER.Hospital_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.List;

public class SubmitFeedbackController implements InitializeUsername{

    public Button submitted;
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<String> hospitalComboBox1;
    @FXML
    private ComboBox<String> doctorComboBox;
    @FXML
    private ChoiceBox<String> experienceChoiceBox;
    @FXML
    private TextField recommendationsField;
    @FXML
    private TextField commentsField;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Patient current;

    private Hospital_Handler hospitalHandler;
    private Doctor_Handler doctorHandler;

    // Initialize method to populate choices and combo boxes
    public void initialize(String username) {
        current= new Patient(username);
        hospitalHandler = new Hospital_Handler();
        doctorHandler = new Doctor_Handler();

        // Use Task to fetch hospital names asynchronously
        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                return hospitalHandler.getHospitalNames();
            }
        };

        // When the data is successfully fetched, populate ComboBox
        task.setOnSucceeded(e -> {
            List<String> hospitalNames = task.getValue();
            System.out.println("Fetched hospitals: " + hospitalNames); // Debugging line
            if (hospitalNames != null && !hospitalNames.isEmpty()) {
                hospitalComboBox1.getItems().addAll(hospitalNames);
            } else {
                feedbackLabel.setText("No hospitals found.");
            }
        });

        // Handle failure to fetch hospital names
        task.setOnFailed(e -> {
            e.getSource().getException().printStackTrace();
            feedbackLabel.setText("Failed to load hospitals.");
        });

        // Start the task on a separate thread
        new Thread(task).start();

        // Populate experience choice box with options
        experienceChoiceBox.getItems().addAll("Excellent", "Good", "Average", "Poor");

        // Add listener to update doctorComboBox based on selected hospital
        hospitalComboBox1.setOnAction(event -> {
            String selectedHospital = hospitalComboBox1.getValue();
            loadDoctorsForHospital(selectedHospital);
        });
    }

    // Method to load doctors based on selected hospital
    private void loadDoctorsForHospital(String hospitalName) {
        if (hospitalName != null && !hospitalName.isEmpty()) {
            System.out.println("Loading doctors for hospital: " + hospitalName); // Debugging line
            Task<List<Doctor>> task = new Task<List<Doctor>>() {
                @Override
                protected List<Doctor> call() throws Exception {
                    return Doctor.getDoctorsByHospital(hospitalName);
                }
            };

            task.setOnSucceeded(e -> {
                List<Doctor> doctors = task.getValue();
                System.out.println("Fetched doctors: " + doctors); // Debugging line
                doctorComboBox.getItems().clear(); // Clear previous items
                if (doctors != null && !doctors.isEmpty()) {
                    for (Doctor doctor : doctors) {
                        doctorComboBox.getItems().add(doctor.getName()); // Add doctor names to the combo box
                    }
                } else {
                    feedbackLabel.setText("No doctors found for this hospital.");
                }
            });

            task.setOnFailed(e -> {
                e.getSource().getException().printStackTrace();
                feedbackLabel.setText("Failed to load doctors.");
            });

            new Thread(task).start();
        }
    }

    // Handle the feedback submission
    @FXML
    public void handleSubmit(ActionEvent actionEvent) {
        // Get input values from the form
        int patientid = current.getId();
        String patientName = current.getFirstName();  // Assuming you have a TextField for patient name
        String doctorName = doctorComboBox.getValue();
        String hospitalName = hospitalComboBox1.getValue();
        String experience = experienceChoiceBox.getValue();
        String recommendations = recommendationsField.getText();
        String comments = commentsField.getText();

        // Validate if all required fields are filled
        if ( doctorName == null || hospitalName == null || experience == null || recommendations.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        // Convert experience string (Yes/No) to boolean for database
        boolean experienceRating = experience.equalsIgnoreCase("Yes");

        // Create an instance of Feedback_Handler to save the data
        Feedback_Handler feedbackHandler = new Feedback_Handler();

        // Call the insertFeedback method and check if the operation was successful
        boolean success = Feedback.insertFeedback(patientid,patientName, doctorName, hospitalName, experienceRating, recommendations, comments);

        // Show success or failure message
        if (success) {
            showFeedbackSuccess();
        } else {
            showError("Failed to submit feedback. Please try again.");
        }
    }

    // Method to show success alert
    private void showFeedbackSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feedback Submitted");
        alert.setHeaderText("Thank you for your feedback!");
        alert.setContentText("Your feedback has been successfully submitted.");
        alert.showAndWait();
    }

    // Method to show error alert
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Submission Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Handle the back button action (your existing method)
    public void HandleBack(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Patient Home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();
            // Get the controller for the PatientHome screen
            PatientHomeController controller = loader.getController();
            controller.initialize(current.getUsername());

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene newScene = new Scene(newPage);
            currentStage.setScene(newScene);
            currentStage.setTitle("Patient Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception if the FXML fails to load
        }
    }
}
