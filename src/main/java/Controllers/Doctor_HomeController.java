package Controllers;

import OOP.Doctor;
import OOP.DoctorNotification;
import OOP.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Doctor_HomeController implements InitializeUsername {

    public Button appoint;
    public Button VidCon;
    public Button docfaq;
    public Button feed;
    public Button staff;
    public Button LogoutButton;
    public Button Feedback;

    @FXML
    private Label DoctorName;

    @FXML
    private Doctor currentDoctor;

    @FXML
    private Menu notificationMenu; // Menu to show notifications

    @FXML
    private String username;

    public void initialize(String username) {
        this.username = username;
        currentDoctor = new Doctor(username);
        DoctorName.setText("Welcome, " + username); // Set the label text to display the username
        System.out.println("Doctor logged in with username: " + currentDoctor.getUsername() +
                " and name: " + currentDoctor.getName());

        // Load notifications when the doctor logs in
        loadNotifications();
    }

    private void loadPage(String fxmlFile, String title, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass username to controllers implementing InitializeUsername
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentDoctor.getUsername());
            }

            Stage currentStage = (Stage) sourceButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Event handlers for different actions
    public void handleAppointment(ActionEvent actionEvent) {
        loadPage("/UI/Doctor_ManageAppointment.fxml", "Manage Appointment", appoint);
    }

    public void handlevid(ActionEvent actionEvent) {
        loadPage("/UI/Doctor_videoConsultation.fxml", "Video Consultation", VidCon);
    }

    public void handledocfaq(ActionEvent actionEvent) {
        loadPage("Doctor_Faq.fxml", "FAQs", docfaq);
    }

    public void Gofeedback(ActionEvent actionEvent) {
        loadPage("/UI/Doctor_Feedback.fxml", "Doctor Feedback Page", Feedback);
    }

    public void handlestaff(ActionEvent actionEvent) {
        loadPage("/UI/Doctor_StaffSchedule.fxml", "Staff Scheduling", staff);
    }

    public void HandleLogout(ActionEvent actionEvent) {
        loadPage("/UI/HomePage.fxml", "Home Page", LogoutButton);
        System.out.println("Doctor logged out");
    }

    // Load notifications for the current doctor
    private void loadNotifications() {
        try {
            // Use polymorphic behavior to get notifications
            DoctorNotification notificationHandler = new DoctorNotification(0, currentDoctor.getId(), "", false);
            List<Notification> notifications = notificationHandler.getNotifications(currentDoctor.getId());

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
