package com.example.temp.DB_HANDLER;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class addHospital_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Ensure database name is correct
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query to insert a new hospital
    private static final String INSERT_QUERY =
            "INSERT INTO hospitals (Name) VALUES (?)";

    // SQL query to retrieve hospital names
    private static final String SELECT_QUERY =
            "SELECT Name FROM hospitals";

    /**
     * Adds a hospital to the database.
     *
     * @param name The hospital's name
     * @return true if the hospital was added successfully, false otherwise
     */
    public boolean addHospital(String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            // Set the name parameter for the prepared statement
            preparedStatement.setString(1, name);

            // Execute the update (INSERT)
            int rowsAffected = preparedStatement.executeUpdate();

            // If rows were affected, the hospital was added successfully
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all hospital names from the database.
     *
     * @return A list of hospital names
     */
    public List<String> getHospitalNames() {
        List<String> hospitalNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

            // Iterate through the results and add names to the list
            while (resultSet.next()) {
                hospitalNames.add(resultSet.getString("Name")); // Adjust column name if necessary
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }

        return hospitalNames;
    }
}
