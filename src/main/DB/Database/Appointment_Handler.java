package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Appointment_Handler implements DatabaseConfig{


    public boolean saveAppointment(String status, String date, String time, int doctorId, int patientId) {
        String insertQuery = "INSERT INTO appointment (status, date, time, doctor_id, patient_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set parameters for the query
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            preparedStatement.setInt(4, doctorId);
            preparedStatement.setInt(5, patientId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }
    public boolean deleteAppointment(int appointmentId) {
        String deleteQuery = "DELETE FROM appointment WHERE appointmentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Set the appointmentId parameter for the query
            preparedStatement.setInt(1, appointmentId);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the deletion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }

    public List<String> getBookedTimeSlots(int doctorId, String date) {
        List<String> bookedSlots = new ArrayList<>();
        String query = "SELECT time FROM appointment WHERE doctor_id = ? AND date = ? AND status = 'Pending'";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, date);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Collect booked time slots
            while (resultSet.next()) {
                bookedSlots.add(resultSet.getString("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedSlots; // Return the list of booked time slots
    }

    public ObservableList<Object[]> getPendingAppointments(int doctorId) {
        ObservableList<Object[]> pendingAppointments = FXCollections.observableArrayList();  // Observable list to hold the result
        String query = "SELECT appointmentID, status, date, time, patient_id FROM appointment WHERE doctor_id = ? AND status = 'Pending'";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the doctorId parameter for the query
            preparedStatement.setInt(1, doctorId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and map the data to Object[]
            while (resultSet.next()) {
                Object[] appointment = new Object[5];  // Adjusted for 5 fields instead of 6
                appointment[0] = resultSet.getInt("appointmentID");  // Appointment ID
                appointment[1] = resultSet.getString("status");  // Status
                appointment[2] = resultSet.getString("date");  // Date
                appointment[3] = resultSet.getString("time");  // Time
                appointment[4] = resultSet.getInt("patient_id");  // Patient ID

                // Add the appointment to the list
                pendingAppointments.add(appointment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pendingAppointments;  // Return the list of pending appointments
    }





}
