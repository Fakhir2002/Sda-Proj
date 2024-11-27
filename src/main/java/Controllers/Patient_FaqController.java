package Controllers;

import OOP.Doctor;
import OOP.Faq;
import OOP.Patient;
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

public class Patient_FaqController implements InitializeUsername {

    @FXML
    private Button ASK;

    @FXML
    private TableColumn<Faq, String> Answer;

    @FXML
    private TableColumn<Faq, String> Question;

    @FXML
    private TableView<Faq> faqview;

    @FXML
    private TableColumn<Faq, String> Doctorcol;

    @FXML
    private ComboBox<Doctor> doctorbox;

    @FXML
    private TextField question;

    @FXML
    private Button cancelFAQ;

    @FXML
    private Faq faq;

    private Patient currentPatient;

    // Initialize method
    public void initialize(String username) {
        currentPatient = new Patient(username); // Initialize patient with username
        faq = new Faq(); // Initialize FAQ class

        // Initialize the FAQ handler
        populateDoctorComboBox(); // Populate doctor ComboBox
        setupTableView(); // Setup FAQ TableView
    }

    // Populate ComboBox with doctors from Faq
    private void populateDoctorComboBox() {
        List<Doctor> doctors = faq.getAllDoctors(); // Retrieve the list of doctors
        ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList(doctors);

        doctorbox.setItems(doctorObservableList);

        // Set a custom cell factory to display the doctor's name in the ComboBox
        doctorbox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Doctor doctor, boolean empty) {
                super.updateItem(doctor, empty);
                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getName()); // Display the doctor's name
                }
            }
        });

        // Set the selected item's display in the ComboBox
        doctorbox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Doctor doctor, boolean empty) {
                super.updateItem(doctor, empty);
                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getName()); // Display the doctor's name
                }
            }
        });

        // Set default value if the list is not empty
        if (!doctorObservableList.isEmpty()) {
            doctorbox.setValue(doctorObservableList.get(0));
        }
    }

    // Setup the TableView with FAQ data
    private void setupTableView() {
        Question.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuestion()));
        Answer.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswer()));

        // Populate with existing FAQs
        refreshTableView();
    }

    // Refresh TableView with the updated FAQ list
    private void refreshTableView() {
        List<Faq> faqs = Faq.getAllFaqs();
        ObservableList<Faq> faqList = FXCollections.observableArrayList(faqs);
        faqview.setItems(faqList);
    }

    // Handle the ASK button click
    public void HandleASK(ActionEvent actionEvent) {
        try {
            Doctor selectedDoctor = doctorbox.getValue(); // Get selected doctor
            String questionText = question.getText().trim();

            if (selectedDoctor == null || questionText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Please select a doctor and enter a question!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Create a new FAQ and insert it into the database
            Faq newFaq = new Faq();
            newFaq.setPatientID(currentPatient.getId());
            newFaq.setDoctorID(selectedDoctor.getId());
            newFaq.setQuestion(questionText);
            newFaq.setAnswer(""); // Default to no answer initially

            if (faq.insertFaq(newFaq)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Question submitted successfully!", ButtonType.OK);
                alert.showAndWait();

                // Refresh TableView
                refreshTableView();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Failed to submit question. Please try again!", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "An error occurred while submitting your question!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Handle cancellation
    public void cancellation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/PatientHome.fxml"));
            Parent newPage = loader.load();

            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            Stage currentStage = (Stage) cancelFAQ.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Patient's Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
