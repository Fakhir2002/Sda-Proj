package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ManageAppointment_Handler implements DatabaseConfig{

    // Method to fetch all appointments from the database
    public ObservableList<Object[]> getAppointments(int doctorId) {
        ObservableList<Object[]> appointments = FXCollections.observableArrayList();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/user"; // Update with your DB name
        String user = "root";  // Update with your DB user
        String password = "12345678"; // Update with your DB password

        // SQL query to fetch data from the appointments table where doctor_id matches
        String sql = "SELECT appointmentID, patient_id, date, time, status FROM appointment WHERE doctor_id = ?";

        // Fetch data from the database and populate the appointments list
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the doctor_id parameter in the query
            pstmt.setInt(1, doctorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Retrieve data from the result set
                    int id = rs.getInt("appointmentID");
                    String patientId = String.valueOf(rs.getInt("patient_id"));
                    String date = rs.getString("date");
                    String time = rs.getString("time");
                    String status = rs.getString("status");

                    // Add to ObservableList as an Object array
                    appointments.add(new Object[]{id, patientId, date, time, status});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database connection or SQL errors
        }

        return appointments; // Return the list of appointments
    }




    public boolean updateAppointmentStatus(int appointmentId) {
        // SQL query to update the status of an appointment
        String sql = "UPDATE appointment SET status = ? WHERE appointmentID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "confirmed"); // Set the status to "confirmed"
            pstmt.setInt(2, appointmentId); // Set the appointment ID

            int rowsAffected = pstmt.executeUpdate();

            // If rows were affected, return true indicating success
            if (rowsAffected > 0) {
                System.out.println("Appointment status updated to confirmed.");
                return true;
            } else {
                System.out.println("Appointment not found.");
                return false; // If no rows were updated, return false
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there is a SQL exception
        }
    }


    public int getPatientIdByAppointmentId(int appointmentId) {
        String query = "SELECT patient_id FROM appointment WHERE appointmentID = ?";
        int patientId = -1;  // Default value to return if no patient is found

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the appointmentId parameter for the query
            preparedStatement.setInt(1, appointmentId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, get the patient_id
            if (resultSet.next()) {
                patientId = resultSet.getInt("patient_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patientId;  // Return the patientId (or -1 if not found)
    }



}
