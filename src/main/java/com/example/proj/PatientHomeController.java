package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PatientHomeController implements InitializeUsername {

    @FXML
    private Button faqsButton;

    @FXML
    private Button BookAppointment;

    @FXML
    private Button Compare;

    @FXML
    private Button Feedback;

    @FXML
    private Button emergency;

    @FXML
    private Button payment;

    @FXML
    private Button hello;

    @FXML
    private Button patlogout;

    @FXML
    private Label PatientName;

    @FXML
    private Patient currentPatient;

    @FXML
    private Menu notificationMenu; // Updated to MenuButton

    @FXML
    private String username;

    // Setter to pass the username from the login screen to this controller
    public void initialize(String username) {
        this.username = username;
        currentPatient = new Patient(username);

        // Load notifications
        loadNotifications();

        PatientName.setText("Welcome, " + username);
        System.out.println("Patient logged in with username: " + currentPatient.getUsername() +
                " and name: " + currentPatient.getFirstName());
    }

    // Load the corresponding page based on the button clicked
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass the username to the controllers where necessary
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentPatient.getUsername());
            }

            Stage currentStage = (Stage) currentButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Event handlers for different buttons
    public void HandleBookAppointment(ActionEvent actionEvent) {
        loadPage("Patient_BookAppointment.fxml", "Book Appointment", BookAppointment);
    }

    public void HandleCompare(ActionEvent actionEvent) {
        loadPage("Paitent_Comparison.fxml", "Comparison", Compare);
    }

    public void chalochalo(ActionEvent actionEvent) {
        loadPage("Patient_SubscribePackage.fxml", "Subscribe Package", hello);
    }

    public void HandleFeedback(ActionEvent actionEvent) {
        loadPage("Patient_SubmitFeedback.fxml", "Feedback", Feedback);
    }

    public void HandleEmergency(ActionEvent actionEvent) {
        loadPage("Patient_Emergency.fxml", "Emergency", emergency);
    }

    public void HandleFaqs(ActionEvent actionEvent) {
        loadPage("Patient_Faq.fxml", "FAQs", faqsButton);
    }

    public void HandlePayments(ActionEvent actionEvent) {
        loadPage("Patient_PayBills.fxml", "Bills", payment);
    }

    public void handlepatlogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            System.out.println("Patient logged out");

            Stage currentStage = (Stage) patlogout.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Load notifications for the current patient
    private void loadNotifications() {
        try {
            // Use polymorphic behavior to get notifications
            PatientNotification notificationHandler = new PatientNotification(0, currentPatient.getId(), "", false);
            List<Notification> notifications = notificationHandler.getNotifications(currentPatient.getId());

            notificationMenu.getItems().clear(); // Clear any previous items
            boolean hasUnread = false;

            for (Notification notification : notifications) {
                MenuItem item = new MenuItem(notification.getDescription());

                // Highlight if unread
                if (!notification.isRead()) {
                    hasUnread = true;
                    item.setStyle("-fx-text-fill: red;"); // Highlight unread in red
                }

                // Add action to view notification
                item.setOnAction(event -> viewNotification(notification));
                notificationMenu.getItems().add(item);

                // Add delete option
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setStyle("-fx-text-fill: black; -fx-font-size: 12;");
                deleteItem.setOnAction(event -> deleteNotification(notification));
                notificationMenu.getItems().add(deleteItem);
            }

            // Update Notification menu style
            if (hasUnread) {
                notificationMenu.setStyle("-fx-background-color: red;"); // Highlight if unread
            } else {
                notificationMenu.setStyle("-fx-background-color: white;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mark the notification as read and show its details
    private void viewNotification(Notification notification) {
        try {
            notification.markAsRead(); // Polymorphic behavior

            // Display the notification details
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Notification Details");
            alert.setContentText(notification.getDescription());
            alert.showAndWait();

            // Reload notifications to update the UI
            loadNotifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete a notification
    private void deleteNotification(Notification notification) {
        try {
            if (notification.delete()) { // Polymorphic behavior
                System.out.println("Notification deleted successfully.");
                loadNotifications(); // Refresh the menu
            } else {
                System.out.println("Failed to delete the notification.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
