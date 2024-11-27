package Controllers;

import OOP.Notification;
import OOP.Staff;
import OOP.StaffNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StaffHomeController implements InitializeUsername {

    @FXML
    private Button InventoryButton;

    @FXML
    private Button ResourcesButton;

    @FXML
    private Button StaffLogoutButton;

    @FXML
    private Label staffName;

    @FXML
    private String username;

    @FXML
    private Staff currentStaff;

    @FXML
    private Menu notificationMenu; // Changed to MenuButton for dynamic notifications

    @Override
    public void initialize(String username) {
        this.username = username;
        currentStaff = new Staff(username);

        staffName.setText("Welcome, " + username);
        System.out.println("Staff logged in with username: " + currentStaff.getUsername() +
                " and name: " + currentStaff.getFirstName());

        // Load notifications for the staff
        loadNotifications();
    }

    // Method to load the corresponding page based on the button clicked
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass the username to the controllers where necessary
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentStaff.getUsername());
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
    public void HandleManageInventory(ActionEvent actionEvent) {
        loadPage("/UI/Staff_ManageInventory.fxml", "Manage Inventory", InventoryButton);
    }

    public void AllocateResourceButton(ActionEvent actionEvent) {
        loadPage("/UI/Staff_AllocateResources.fxml", "Allocate Resources", ResourcesButton);
    }

    public void HandleStaffLogout(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/HomePage.fxml"));
            Parent newPage = loader.load();

            System.out.println("Staff logged out");

            Stage currentStage = (Stage) StaffLogoutButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Method to load notifications for the current staff
    private void loadNotifications() {
        try {
            // Use polymorphic behavior to get staff-specific notifications
            StaffNotification notificationHandler = new StaffNotification(0, currentStaff.getId(), "", false);
            List<Notification> notifications = notificationHandler.getNotifications(currentStaff.getId());

            notificationMenu.getItems().clear(); // Clear any previous items
            boolean hasUnread = false;

            for (Notification notification : notifications) {
                MenuItem item = new MenuItem(notification.getDescription());

                // Highlight if unread
                if (!notification.isRead()) {
                    hasUnread = true;
                    item.setStyle("-fx-text-fill: red;"); // Highlight unread notifications in red
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
                notificationMenu.setStyle("-fx-background-color: red;"); // Highlight menu if unread notifications exist
            } else {
                notificationMenu.setStyle("-fx-background-color: white;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view the notification (mark as read)
    private void viewNotification(Notification notification) {
        try {
            notification.markAsRead(); // Polymorphic method call

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

    // Method to delete a notification
    private void deleteNotification(Notification notification) {
        try {
            if (notification.delete()) { // Polymorphic method call
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
