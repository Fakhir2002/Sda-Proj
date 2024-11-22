package com.example.temp.DB_HANDLER;

import com.example.proj.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ManageAppointment_Handler {

    // Method to fetch all appointments from the database
    public ObservableList<Object[]> getAppointments() {
        ObservableList<Object[]> appointments = FXCollections.observableArrayList();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/user"; // Update with your DB name
        String user = "root";  // Update with your DB user
        String password = "12345678"; // Update with your DB password

        // SQL query to fetch data from appointments table
        String sql = "SELECT * FROM appointment"; // Modify this as needed to match your database schema

        // Fetch data from the database and populate the appointments list
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Retrieve data from the result set
                int id = rs.getInt("appointmentID");
                String name = String.valueOf(rs.getInt("patient_id"));
                String date = rs.getString("date");
                String time = rs.getString("time");
                String status = rs.getString("status");

                // Add to ObservableList as an Object array
                appointments.add(new Object[]{id, name, date, time, status});
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database connection or SQL errors
        }

        return appointments; // Return the list of appointments
    }



    public void updateAppointmentStatus(int appointmentId) {
        String url = "jdbc:mysql://localhost:3306/user"; // Update with your DB name
        String user = "root";  // Update with your DB user
        String password = "12345678"; // Update with your DB password

        // SQL query to update the status of an appointment
        String sql = "UPDATE appointment SET status = ? WHERE appointmentID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "confirmed"); // Set the status to "confirmed"
            pstmt.setInt(2, appointmentId); // Set the appointment ID

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment status updated to confirmed.");
            } else {
                System.out.println("Appointment not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
