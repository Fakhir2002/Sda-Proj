package Database;

import com.example.proj.Bill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayBill_Handler implements DatabaseConfig{
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
    public boolean addPayment(int PaitientID, String description, double amount, String status) {
        String sql = "INSERT INTO Payment (paitentID,Description, Amount, Status) VALUES (?,?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, PaitientID);
            statement.setString(2, description);
            statement.setDouble(3, amount);
            statement.setString(4, status);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all payments for a specific patient ID
    public List<Bill> getAllPayments(int patientID) {
        List<Bill> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE paitentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the patientID parameter in the query
            statement.setInt(1, patientID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int paymentID = resultSet.getInt("paymentID");
                    String description = resultSet.getString("Description");
                    double amount = resultSet.getDouble("Amount");
                    String status = resultSet.getString("Status");

                    // Create a Bill object and add it to the list
                    payments.add(new Bill(paymentID, description, amount, status));
                }
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
