package Database;

import com.example.proj.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notification_Handler implements DatabaseConfig {

    // Save a new notification in the database
    public boolean saveNotification(Integer patientId, Integer doctorId, Integer staffId, String description) {
        String insertQuery = "INSERT INTO notification (patient_id, doctor_id, staff_id, description, isRead) VALUES (?, ?, ?, ?, 0)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, patientId, Types.INTEGER);  // Allows null for patientId
            preparedStatement.setObject(2, doctorId, Types.INTEGER);   // Allows null for doctorId
            preparedStatement.setObject(3, staffId, Types.INTEGER);    // Allows null for staffId
            preparedStatement.setString(4, description);

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
        String query = "SELECT * FROM notification WHERE patient_id = ? AND doctor_id IS NULL AND staff_id IS NULL";

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
    public List<Notification> getNotificationsByDoctorId(int doctorId) {
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

    // Get all notifications for a specific staff member
    public List<Notification> getNotificationsByStaffId(int staffId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notification WHERE staff_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, staffId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notifications.add(mapToNotification(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving notifications for staff: " + e.getMessage());
        }
        return notifications;
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

    // Map result set to Notification object
    private Notification mapToNotification(ResultSet resultSet) throws SQLException {
        int notificationId = resultSet.getInt("NotificationID");
        String description = resultSet.getString("description");
        boolean isRead = resultSet.getInt("isRead") == 1;

        Integer patientId = (resultSet.getObject("patient_id") != null) ? resultSet.getInt("patient_id") : null;
        Integer doctorId = (resultSet.getObject("doctor_id") != null) ? resultSet.getInt("doctor_id") : null;
        Integer staffId = (resultSet.getObject("staff_id") != null) ? resultSet.getInt("staff_id") : null;

        return NotificationFactory.createNotification(notificationId, description, isRead, patientId, doctorId, staffId);
    }

    // Delete a notification by ID
    public boolean delete(int notificationId) {
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
}
