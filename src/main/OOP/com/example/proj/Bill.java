package com.example.proj;

import com.example.temp.DB_HANDLER.PayBill_Handler;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Bill {

    private  SimpleIntegerProperty paymentID; // Change to SimpleIntegerProperty for paymentID
    private  SimpleStringProperty description;
    private  SimpleDoubleProperty amount;
    private  SimpleStringProperty status;
    private PayBill_Handler paymentHandler;

    // Constructor to initialize the Bill object
    public Bill(int paymentID, String description, double amount, String status) {
        this.paymentID = new SimpleIntegerProperty(paymentID); // Initialize paymentID as integer
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
    }

    public Bill() {
        paymentHandler= new PayBill_Handler();
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

    // Getter and Setter for amount
    public double getAmount() {
        return amount.get();
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

    public List<Bill> getAllPayments() {
        return paymentHandler.getAllPayments();
    }

    public boolean updatePaymentStatus(int paymentID, String paid) {
        return paymentHandler.updatePaymentStatus(paymentID,paid);
    }

    public boolean addPayment(String description, double amount, String status) {
        return paymentHandler.addPayment(description, amount, status);
    }
}
