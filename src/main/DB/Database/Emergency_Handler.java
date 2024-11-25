package Database;

import com.example.proj.Emergency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Emergency_Handler implements DatabaseConfig{

    // Method to fetch hospital names from the database
    public List<String> getHospitalNames() {
        List<String> hospitalNames = new ArrayList<>();
        String query = "SELECT Name FROM hospitals";  // Adjust based on your actual database schema

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                hospitalNames.add(rs.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospitalNames;
    }

    // Method to insert emergency data into the "emergency" table
    public boolean insertEmergency(int patientId, int hospital_id, String type, String status, String description) {
        String query = "INSERT INTO emergency (patient_id, hospital_id, type, status, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, patientId);
            pstmt.setInt(2, hospital_id);
            pstmt.setString(3, type);
            pstmt.setString(4, status);
            pstmt.setString(5, description);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Returns true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Returns false in case of error
        }
    }

    public List<Emergency> getEmergencyData() {
        List<Emergency> emergencyList = new ArrayList<>();
        String query = "SELECT * from emergency";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                emergencyList.add(new Emergency(
                        rs.getInt("emergency_id"),  // This should match the data type of emergency_id
                        rs.getInt("patient_id"),     // Corrected to int
                        rs.getInt("hospital_id"),    // Corrected to int (no need for String.valueOf)
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("description")
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emergencyList;
    }

    public boolean updateEmergencyStatus(int emergencyId, String newStatus) {
        String query = "UPDATE emergency SET status = ? WHERE emergency_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, emergencyId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if the update was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false in case of error
        }
    }


    // You can add additional methods for handling Emergency-related operations
}