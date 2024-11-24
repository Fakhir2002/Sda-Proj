package com.example.temp.DB_HANDLER;

import com.example.proj.Bill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayBill_Handler {

    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "12345678"; // Database password
    private static Connection connection;

    // Constructor to initialize the database connection (you can use a connection pool in real applications)
    public PayBill_Handler() {
        try {
            // Establish the connection to the database
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a payment to the database
    public boolean addPayment(String description, double amount, String status) {
        String sql = "INSERT INTO Payment (Description, Amount, Status) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, description);
            statement.setDouble(2, amount);
            statement.setString(3, status);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all payments from the database
    public List<Bill> getAllPayments() {
        List<Bill> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int paymentID = resultSet.getInt("paymentID");
                String description = resultSet.getString("Description");
                double amount = resultSet.getDouble("Amount");
                String status = resultSet.getString("Status");

                payments.add(new Bill(paymentID, description, amount, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    // Method to update the status of a payment
    public boolean updatePaymentStatus(int paymentID, String newStatus) {
        String sql = "UPDATE Payment SET Status = ? WHERE paymentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setInt(2, paymentID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a payment from the database
    public boolean deletePayment(int paymentID) {
        String sql = "DELETE FROM Payment WHERE paymentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, paymentID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
