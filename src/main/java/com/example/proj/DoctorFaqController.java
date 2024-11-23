package com.example.proj;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DoctorFaqController implements InitializeUsername{

    @FXML
    private Button cancelFAQ;

    @FXML
    private TableColumn<Faq, String> AnswerCol;

    @FXML
    private TableColumn<Faq, String> QuestionCol;

    @FXML
    private TableView<Faq> faqTable;

    @FXML
    private TextField answerText;

    @FXML
    private Button answerbutton;

    @FXML
    private Faq faq;

    private Doctor currentDoctor;

    private ObservableList<Faq> faqObservableList;

    public void initialize(String username) {
        // Initialize the current doctor and handler
        currentDoctor = new Doctor(username);
        faq = new Faq();


        // Set up the table view
        setupTableView();

        // Load FAQs for the current doctor
        loadDoctorFAQs();
    }

    private void setupTableView() {
        // Configure TableView columns
        QuestionCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuestion()));
        AnswerCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswer()));

        // Initialize the observable list
        faqObservableList = FXCollections.observableArrayList();
        faqTable.setItems(faqObservableList);

        // Listener for TableView selection
        faqTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                answerText.setText(newValue.getAnswer());
            }
        });
    }

    private void loadDoctorFAQs() {
        // Fetch FAQs assigned to the current doctor
        List<Faq> doctorFaqs = faq.getFaqsByDoctorId(currentDoctor.getId());

        // Update the observable list to reflect database data
        faqObservableList.setAll(doctorFaqs);
    }

    @FXML
    void HandleAnswer(ActionEvent event) {
        // Get the selected FAQ
        Faq selectedFaq = faqTable.getSelectionModel().getSelectedItem();

        if (selectedFaq == null) {
            // Alert if no FAQ is selected
            showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a question to answer.");
            return;
        }

        String answer = answerText.getText().trim();

        if (answer.isEmpty()) {
            // Alert if the answer field is empty
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Answer cannot be empty.");
            return;
        }

        // Set the answer for the selected FAQ
        selectedFaq.setAnswer(answer);

        // Update the FAQ in the database
        boolean updateSuccess = faq.updateFaqAnswer(
                selectedFaq.getPatientID(),
                selectedFaq.getDoctorID(),
                selectedFaq.getAnswer()
        );

        if (updateSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Answer submitted successfully!");

            // Refresh the TableView
            faqTable.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Error", "Failed to submit answer. Please try again.");
        }
    }

    @FXML
    public void cancellation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent newPage = loader.load();


            DoctorHomeController controller = loader.getController();

            controller.initialize(currentDoctor.getUsername());


            // Get the current stage and set the new scene
            Stage currentStage = (Stage) cancelFAQ.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
