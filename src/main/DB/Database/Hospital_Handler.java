package Database;

import com.example.proj.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Hospital_Handler implements DatabaseConfig{


    // SQL query to insert a new hospital
    private static final String INSERT_QUERY =
            "INSERT INTO hospitals (name) VALUES (?)";

    // SQL query to retrieve all hospital names
    private static final String SELECT_QUERY =
            "SELECT id, name FROM hospitals"; // Query to retrieve hospital ID and name

    // SQL query to retrieve hospital details by ID
    private static final String SELECT_BY_ID_QUERY =
            "SELECT id, name FROM hospitals WHERE id = ?";

    // SQL query to update hospital details (optional functionality)
    private static final String UPDATE_QUERY =
            "UPDATE hospitals SET name = ? WHERE id = ?";

    // SQL query to delete a hospital by ID (optional functionality)
    private static final String DELETE_QUERY =
            "DELETE FROM hospitals WHERE id = ?";

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
     * Retrieves all hospitals from the database.
     *
     * @return A list of hospitals
     */
    public List<Hospital> getAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

            // Iterate through the result set and create Hospital objects
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                hospitals.add(new Hospital(id, name));  // Create a new Hospital object
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }

        return hospitals;
    }

    /**
     * Retrieves the details of a hospital by ID.
     *
     * @param id The hospital's ID
     * @return A Hospital object containing the details, or null if not found
     */
    public Hospital getHospitalDetails(String id) {
        Hospital hospital = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

            // Set the ID parameter for the prepared statement
            preparedStatement.setString(1, id);

            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a hospital is found, create a new Hospital object and set the details
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                hospital = new Hospital(id, name);  // Create a Hospital object with ID and name
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }

        return hospital;  // Return the Hospital object, or null if not found
    }

    /**
     * Updates hospital details in the database.
     *
     * @param id   The hospital's ID
     * @param name The new name of the hospital
     * @return true if the update was successful, false otherwise
     */
    public boolean updateHospital(String id, String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a hospital from the database by ID.
     *
     * @param id The hospital's ID
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteHospital(String id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            // Set the ID parameter for the prepared statement
            preparedStatement.setString(1, id);

            // Execute the delete
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getHospitalNames() {
        List<String> hospitalNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

            // Iterate through the result set and add hospital names to the list
            while (resultSet.next()) {
                hospitalNames.add(resultSet.getString("Name")); // Add name to list
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }

        return hospitalNames;
    }


    // SQL query to retrieve hospital details by name
    private static final String SELECT_HOSPITAL_ID_BY_NAME_QUERY =
            "SELECT id FROM hospitals WHERE name = ?";  // Query to fetch hospital id by name

    /**
     * Retrieves the hospital ID based on the hospital name.
     *
     * @param hospitalName The name of the hospital
     * @return The hospital ID if found, or -1 if not found
     */
    public int getHospitalIdByName(String hospitalName) {
        int hospitalId = -1;  // Default value if hospital is not found

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HOSPITAL_ID_BY_NAME_QUERY)) {

            // Set the hospital name parameter for the prepared statement
            preparedStatement.setString(1, hospitalName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, retrieve the hospital ID
            if (resultSet.next()) {
                hospitalId = resultSet.getInt("id");  // Get hospital ID
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }

        return hospitalId;  // Return the hospital ID, or -1 if not found
    }



}
