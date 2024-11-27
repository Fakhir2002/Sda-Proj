package Database;

import OOP.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Staff_Handler implements DatabaseConfig{


    // SQL queries
    private static final String INSERT_STAFF_QUERY =
            "INSERT INTO staff (first_name, last_name, contact_no, dob, address, username, password, hospital) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String VALIDATE_LOGIN_QUERY =
            "SELECT * FROM staff WHERE username = ? AND password = ?";
    private static final String SELECT_ALL_STAFF_QUERY =
            "SELECT * FROM staff";
    private static final String SELECT_STAFF_BY_HOSPITAL_QUERY =
            "SELECT * FROM staff WHERE hospital = ?";
    private static final String SELECT_STAFF_BY_NAME_QUERY =
            "SELECT * FROM staff WHERE first_name = ? AND last_name = ?";

    private static final String VALIDATE_STAFF_QUERY =
            "SELECT COUNT(*) FROM staff WHERE username = ? AND password = ?";

    /**
     * Registers a new staff member in the database.
     *
     * @param firstName        Staff's first name
     * @param lastName         Staff's last name
     * @param contactNo        Staff's contact number
     * @param dob              Staff's date of birth
     * @param address          Staff's address
     * @param username         Staff's username
     * @param password         Staff's password
     * @param selectedHospital Staff's hospital
     * @return true if registration was successful, false otherwise
     */
    public boolean registerStaff(String firstName, String lastName, String contactNo,
                                 String dob, String address, String username, String password, String selectedHospital) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password); // Consider hashing the password
            preparedStatement.setString(8, selectedHospital);

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates staff login credentials.
     *
     * @param username Staff's username
     * @param password Staff's password
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_LOGIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Compare hashed password if applicable

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a match is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a list of all staff members from the database.
     *
     * @return List of all staff usernames
     */
    public List<String> getAllStaff() {
        List<String> staffList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through the result set
            while (resultSet.next()) {
                staffList.add(resultSet.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    /**
     * Retrieves staff members working at a specific hospital.
     *
     * @param hospitalName The name of the hospital
     * @return List of staff members working at the hospital
     */
    public List<Staff> getStaffByHospital(String hospitalName) {
        List<Staff> staffList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_HOSPITAL_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, hospitalName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set
            while (resultSet.next()) {
                staffList.add(mapResultSetToStaff(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    /**
     * Verifies staff credentials for login.
     *
     * @param username Staff's username
     * @param password Staff's password
     * @return true if the credentials are valid, false otherwise
     */
    public boolean loginStaff(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_STAFF_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Return true if the count is greater than 0
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if login fails
    }

    /**
     * Retrieves staff details by their first and last name.
     *
     * @param firstName Staff's first name
     * @param lastName  Staff's last name
     * @return Staff object if found, null otherwise
     */
    public Staff getStaffByName(String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_NAME_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToStaff(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Helper method to map a ResultSet row to a Staff object
    private Staff mapResultSetToStaff(ResultSet resultSet) throws SQLException {
        return new Staff(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("contact_no"),
                resultSet.getString("dob"),
                resultSet.getString("address"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("hospital")
        );
    }

    public Staff getStaffDetails(String username) {
        String query = "SELECT * FROM staff WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the username parameter
            preparedStatement.setString(1, username);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a match is found, map it to a Staff object
            if (resultSet.next()) {
                return mapResultSetToStaff(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no staff member is found
    }

}
