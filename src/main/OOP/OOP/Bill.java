package OOP;

import Database.PayBill_Handler;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Bill {

    private SimpleIntegerProperty paymentID; // Payment ID
    private SimpleIntegerProperty patientID; // Patient ID
    private SimpleStringProperty description;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty status;
    private PayBill_Handler paymentHandler;

    // Constructor to initialize the Bill object with all required properties
    public Bill(int paymentID, int patientID, String description, double amount, String status) {
        this.paymentID = new SimpleIntegerProperty(paymentID);
        this.patientID = new SimpleIntegerProperty(patientID);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
    }

    public Bill() {
        paymentHandler = new PayBill_Handler();
    }

    public Bill(int paymentID, String description, double amount, String status) {
        this.paymentID = new SimpleIntegerProperty(paymentID);  // Initialize paymentID as an integer
        this.description = new SimpleStringProperty(description); // Initialize description as a string
        this.amount = new SimpleDoubleProperty(amount);  // Initialize amount as a double
        this.status = new SimpleStringProperty(status);  // Initialize status as a string
    }

    // Getter and Setter for paymentID
    public int getPaymentID() {
        return paymentID.get();
    }

    public SimpleIntegerProperty paymentIDProperty() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID.set(paymentID);
    }

    // Getter and Setter for patientID
    public int getPatientID() {
        return patientID.get();
    }

    public SimpleIntegerProperty patientIDProperty() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID.set(patientID);
    }

    // Getter and Setter for description
    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    // Getter and Setter for status
    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    // Method to retrieve all payments
    public List<Bill> getAllPayments(int PaitientID) {
        return paymentHandler.getAllPayments(PaitientID);
    }

    // Method to update the payment status
    public boolean updatePaymentStatus(int paymentID, String status) {
        return paymentHandler.updatePaymentStatus(paymentID, status);
    }

    // Method to add a new payment, using patientID and other relevant fields
    public boolean addPayment(int patientID, String description, double amount, String status) {
        return paymentHandler.addPayment(patientID, description, amount, status);
    }
}
