package com.example.proj;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Patient_PayBillsController implements InitializeUsername {

    @FXML
    private Button BackButton;
    @FXML
    private Button payButton;
    @FXML
    private TableView<Bill> billsTable; // The TableView to show the bills
    @FXML
    private TableColumn<Bill, String> billIdColumn; // Bill ID column
    @FXML
    private TableColumn<Bill, String> billDescColumn; // Bill Description column
    @FXML
    private TableColumn<Bill, Double> amountColumn; // Bill Amount column
    @FXML
    private TableColumn<Bill, String> statusColumn; // Bill Status column

    private Patient currentPatient;

    private Bill payment; // Instance of the PayBill_Handler

    public void initialize(String username) {
        // Initialize the current patient
        currentPatient = new Patient(username);
        payment = new Bill(); // Create an instance of PayBill_Handler
        loadBills(); // Load all bills into the TableView
    }

    // Load all bills into the TableView
    private void loadBills() {
        List<Bill> bills = payment.getAllPayments(currentPatient.getId()); // Fetch all bills from the DB
        ObservableList<Bill> billObservableList = FXCollections.observableArrayList(bills);

        // Set up the columns to bind to the Bill properties
        billIdColumn.setCellValueFactory(cellData -> cellData.getValue().paymentIDProperty().asString());
        billDescColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Set the TableView's items
        billsTable.setItems(billObservableList);
    }

    @FXML
    public void HandleBack(ActionEvent actionEvent) {
        try {
            // Load the Patient Home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();
            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            Stage currentStage = (Stage) BackButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Patient Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    @FXML
    public void handlePay(ActionEvent actionEvent) {
        Bill selectedBill = billsTable.getSelectionModel().getSelectedItem();

        if (selectedBill != null) {
            // Update the status of the selected bill to "Paid"
            boolean isUpdated = payment.updatePaymentStatus(selectedBill.getPaymentID(), "Paid");

            if (isUpdated) {
                // Refresh the table to reflect the status change
                loadBills();
            } else {
                // Handle failure in updating status (show an error message or something)
                System.out.println("Failed to update payment status.");
            }
        } else {
            // Handle the case where no bill is selected
            System.out.println("No bill selected for payment.");
        }
    }
}
