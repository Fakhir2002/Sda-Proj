package Database;

import com.example.proj.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notification_Handler implements DatabaseConfig{

    // Save a new notification in the database
    public boolean saveNotification(int patientId, int doctorId, String description) {
        String insertQuery = "INSERT INTO notification (patient_id, doctor_id, description, isRead) VALUES (?, ?, ?, 0)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, description);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int notificationId = generatedKeys.getInt(1);
                    System.out.println("Notification saved with ID: " + notificationId);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error saving notification: " + e.getMessage());
            return false;
        }
    }

    // Get all notifications for a specific patient
    public List<Notification> getNotificationsByPatient(int patientId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notification WHERE patient_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, patientId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notifications.add(mapToNotification(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving notifications for patient: " + e.getMessage());
        }
        return notifications;
    }

    // Get all notifications for a specific doctor
    public List<Notification> getNotificationsByDoctor(int doctorId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notification WHERE doctor_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, doctorId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notifications.add(mapToNotification(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving notifications for doctor: " + e.getMessage());
        }
        return notifications;
    }

    // Delete a notification by ID
    public boolean deleteNotification(int notificationId) {
        String deleteQuery = "DELETE FROM notification WHERE NotificationID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, notificationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting notification: " + e.getMessage());
            return false;
        }
    }

    // Get a notification by its ID
    public Notification getNotificationById(int notificationId) {
        String query = "SELECT * FROM notification WHERE NotificationID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, notificationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToNotification(resultSet);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving notification by ID: " + e.getMessage());
        }
        return null;
    }

    // Update the description of a notification
    public boolean updateNotificationDescription(int notificationId, String newDescription) {
        String updateQuery = "UPDATE notification SET description = ? WHERE NotificationID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, notificationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating notification: " + e.getMessage());
            return false;
        }
    }

    // Mark a notification as read
    public boolean markAsRead(int notificationId) {
        String updateQuery = "UPDATE notification SET isRead = 1 WHERE NotificationID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, notificationId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
            return false;
        }
    }

    // Utility method to map a ResultSet row to a Notification object
    private Notification mapToNotification(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("NotificationID");
        int patientId = resultSet.getInt("patient_id");
        int doctorId = resultSet.getInt("doctor_id");
        String description = resultSet.getString("description");
        boolean isRead = resultSet.getBoolean("isRead");
        return new Notification(id, patientId, doctorId, description, isRead);
    }
}
