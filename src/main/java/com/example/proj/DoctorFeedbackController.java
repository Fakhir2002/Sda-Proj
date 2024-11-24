package com.example.proj;

import com.example.temp.DB_HANDLER.Feedback_Handler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DoctorFeedbackController extends Application  implements InitializeUsername{

    @FXML
    private Button Backbutton;

    @FXML
    private TableView<Feedback> feedbackTable;

    @FXML
    private TableColumn<Feedback, Integer> idColumn;

    @FXML
    private TableColumn<Feedback, String> patientNameColumn;

    @FXML
    private TableColumn<Feedback, String> doctorNameColumn;

    @FXML
    private TableColumn<Feedback, String> hospitalNameColumn;

    @FXML
    private TableColumn<Feedback, Boolean> experienceRatingColumn;

    @FXML
    private TableColumn<Feedback, String> recommendationsColumn;

    @FXML
    private TableColumn<Feedback, String> feedbackCommentsColumn;

    private Feedback_Handler feedbackHandler = new Feedback_Handler();
    @FXML
    private Doctor currentDoctor;



    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML for this controller
        Parent root = FXMLLoader.load(getClass().getResource("DoctorFeedback.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Feedback Page");
        stage.show();
    }

    @FXML
    public void initialize(String username) {
        currentDoctor = new Doctor(username);

        // Initialize the TableView columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        hospitalNameColumn.setCellValueFactory(new PropertyValueFactory<>("hospitalName"));
        experienceRatingColumn.setCellValueFactory(new PropertyValueFactory<>("experienceRating"));
        recommendationsColumn.setCellValueFactory(new PropertyValueFactory<>("recommendations"));
        feedbackCommentsColumn.setCellValueFactory(new PropertyValueFactory<>("feedbackComments"));

        // Load feedback data into the TableView
        loadFeedbackData();
    }

    /**
     * Loads feedback data from the database and populates the TableView.
     */
    private void loadFeedbackData() {
        // Get the name of the current doctor
        String doctorName = currentDoctor.getName();

        // Fetch feedbacks filtered by the doctor's name
        List<Feedback> feedbackList = Feedback.getFeedbackByDoctorName(doctorName);

        // Convert the list to an ObservableList for the TableView
        ObservableList<Feedback> observableFeedbackList = FXCollections.observableArrayList(feedbackList);

        // Set the items in the TableView
        feedbackTable.setItems(observableFeedbackList);
    }


    @FXML
    public void GoBack(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Doctor Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();

            DoctorHomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) Backbutton.getScene().getWindow();

            // Update the current stage with the new scene
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
