package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Patient_ComparisonController implements  InitializeUsername{

    @FXML
    private ComboBox<String> doctorComboBox1; // ComboBox for Doctor 1
    @FXML
    private ComboBox<String> doctorComboBox2; // ComboBox for Doctor 2

    @FXML
    private TableView<DoctorComparison> doctorTableView; // TableView for comparison
    @FXML
    private TableColumn<DoctorComparison, String> attributeColumn; // Attribute column
    @FXML
    private TableColumn<DoctorComparison, String> doctor1Column; // Doctor 1 column
    @FXML
    private TableColumn<DoctorComparison, String> doctor2Column; // Doctor 2 column

    @FXML
    private DoctorComparison doctorComparison;
    @FXML
    private Feedback feedback;
    @FXML
    private Patient currentPatient;
    @FXML
    private Button backyy;


    public void initialize(String username) {
        currentPatient = new Patient(username);
        doctorComparison = new DoctorComparison();
        feedback=new Feedback();
        // Populate ComboBoxes
        loadDoctorNames();

        // Initialize TableView columns
        attributeColumn.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        doctor1Column.setCellValueFactory(new PropertyValueFactory<>("doctor1Value"));
        doctor2Column.setCellValueFactory(new PropertyValueFactory<>("doctor2Value"));
    }

    private void loadDoctorNames() {
        List<String> doctorNames = doctorComparison.getAllDoctors(); // Fetch doctor names from the database
        if (doctorNames != null) {
            doctorComboBox1.getItems().addAll(doctorNames);
            doctorComboBox2.getItems().addAll(doctorNames);
        }
    }

    @FXML
    public void aageychalo(ActionEvent actionEvent) {
        try {
            // Get selected doctors
            String doctor1Name = doctorComboBox1.getValue();
            String doctor2Name = doctorComboBox2.getValue();

            // Check if either of the doctors is not selected
            if (doctor1Name == null || doctor2Name == null) {
                showAlert("Error", "Please select two doctors for comparison.");
                return; // Exit the method if doctors are not selected
            }

            // Check if the same doctor is selected
            if (doctor1Name.equals(doctor2Name)) {
                showAlert("Error", "You cannot compare the same doctor.");
                return; // Exit the method if the same doctor is selected
            }

            // Fetch doctor details
            Doctor doctor1 = Doctor.getDoctorbyName(doctor1Name);
            Doctor doctor2 = Doctor.getDoctorbyName(doctor2Name);

            // Fetch feedbacks for both doctors
            List<Feedback> feedbacksForDoctor1 = Feedback.getFeedbackByDoctorName(doctor1Name);
            List<Feedback> feedbacksForDoctor2 = Feedback.getFeedbackByDoctorName(doctor2Name);

            // Calculate average feedback ratings
            double avgFeedbackDoctor1 = calculateAverageFeedback(feedbacksForDoctor1);
            double avgFeedbackDoctor2 = calculateAverageFeedback(feedbacksForDoctor2);

            // Clear TableView and populate it with comparison data (including formatted feedback rating)
            doctorTableView.getItems().clear();
            doctorTableView.getItems().addAll(
                    new DoctorComparison("Name", doctor1.getName(), doctor2.getName()),
                    new DoctorComparison("Specialty", doctor1.getSpecialty(), doctor2.getSpecialty()),
                    new DoctorComparison("Hospital", doctor1.getHospital(), doctor2.getHospital()),
                    new DoctorComparison("Average Feedback Rating",
                            formatFeedbackRating(avgFeedbackDoctor1),
                            formatFeedbackRating(avgFeedbackDoctor2))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to calculate average feedback rating
    private double calculateAverageFeedback(List<Feedback> feedbacks) {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0; // No feedback available
        }

        double totalScore = 0;
        int count = 0;

        for (Feedback feedback : feedbacks) {
            String experienceRating = feedback.getExperienceRating();
            switch (experienceRating) {
                case "Excellent":
                    totalScore += 4;
                    break;
                case "Good":
                    totalScore += 3;
                    break;
                case "Average":
                    totalScore += 2;
                    break;
                case "Poor":
                    totalScore += 1;
                    break;
            }
            count++;
        }

        return totalScore / count; // Return average score
    }

    // Method to format feedback rating into descriptive labels
    private String formatFeedbackRating(double rating) {
        if (rating == 0.0) {
            return "No Feedback"; // No feedback available
        } else if (rating > 1 && rating <= 2) {
            return "Average";
        } else if (rating > 2 && rating <= 3) {
            return "Good";
        } else if (rating > 3 && rating < 4) {
            return "Excellent";
        } else if (rating == 4) {
            return "THE BEST";
        }
        return "Undefined"; // Fallback case, should not normally occur
    }




    // Method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void backfromdoccomp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();
            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            Stage currentStage = (Stage) backyy.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
