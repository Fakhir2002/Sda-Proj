package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Inventory_Handler {

    private static final String URL = "jdbc:mysql://localhost:3306/user";  // Replace with your database URL
    private static final String USER = "root";  // If using MySQL, add your username here
    private static final String PASSWORD = "12345678";  // If using MySQL, add your password here

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);  // Correct connection setup
    }

    // This method inserts new inventory data into the database for a specific hospital
    public static void insertInventory(String hospital,int medStock, int stockStock, int miscStock) {
        // SQL query to insert new inventory record
        String sql = "INSERT INTO inventory (hospital,medQuantity, stockQuantity, miscellaniousQuantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Print debugging message to confirm values being inserted
            System.out.println("Inserting new inventory: medStock=" + medStock + ", stockStock=" + stockStock + ", miscStock=" + miscStock );

            // Set the values to the PreparedStatement
            pstmt.setString(1, hospital);
            pstmt.setInt(2, medStock);
            pstmt.setInt(3, stockStock);
            pstmt.setInt(4, miscStock);// Insert the logged-in hospital's ID

            // Execute the insert query
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory inserted successfully!");
            } else {
                System.out.println("Failed to insert inventory.");
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions and print full error details
            System.out.println("Error inserting inventory: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        }
    }
}
