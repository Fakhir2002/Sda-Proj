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

    public List<Emergency> getEmergencyData(int hospitalID) {
        List<Emergency> emergencyList = new ArrayList<>();
        // Updated query to filter by hospital_id
        String query = "SELECT * FROM emergency WHERE hospital_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the hospital_id parameter in the query
            pstmt.setInt(1, hospitalID);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    emergencyList.add(new Emergency(
                            rs.getInt("emergency_id"),  // Emergency ID
                            rs.getInt("patient_id"),     // Patient ID
                            rs.getInt("hospital_id"),    // Hospital ID
                            rs.getString("type"),        // Emergency type
                            rs.getString("status"),      // Emergency status
                            rs.getString("description")  // Description of the emergency
                    ));
                }
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
