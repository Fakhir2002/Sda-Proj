package com.example.temp.DB_HANDLER;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emergency_Handler {

    // Method to fetch hospital names from the database
    public List<String> getHospitalNames() {
        List<String> hospitalNames = new ArrayList<>();
        String query = "SELECT Name FROM hospitals";  // Adjust based on your actual database schema

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "12345678");
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

    // You can add additional methods for handling Emergency-related operations
}
