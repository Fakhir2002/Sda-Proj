package Database;

import com.example.proj.Admin;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin_Handler implements DatabaseConfig{


    private static final String GET_PATIENT_NAMES_QUERY = "SELECT first_name FROM patients";
    private static final String SELECT_DOCTORS_QUERY = "SELECT DoctorID, Name FROM doctors";
    private static final String DELETE_DOCTOR_QUERY =
            "DELETE FROM doctors WHERE DoctorID = ?";
    private static final String DELETE_PATIENT_QUERY =
            "DELETE FROM patients WHERE first_name = ?";
    private static final String DELETE_STAFF_QUERY =
            "DELETE FROM staff WHERE first_name = ?";  // Query to delete staff by first_name

    private static final String LOGIN_QUERY =
            "SELECT * FROM admin WHERE username = ? AND password = ?";

    private static final String INSERT_ADMIN_QUERY =
            "INSERT INTO admin (first_name, last_name, contact_no, dob, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves admin data to the database.
     *
     * @param firstName  Admin's first name
     * @param lastName   Admin's last name
     * @param contactNo  Admin's contact number
     * @param dob        Admin's date of birth (in 'YYYY-MM-DD' format)
     * @param address    Admin's address
     * @param username   Admin's username
     * @param password   Admin's password (consider hashing for security)
     * @return true if the data was successfully inserted, false otherwise
     */
    public static boolean registerAdmin(String firstName, String lastName, String contactNo, String dob,
                                        String address, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password); // Consider encrypting/hashing the password

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies patient login credentials.
     *
     * @param username Patient's username
     * @param password Patient's password (plaintext; consider using hashed comparison)
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if a record was found
                return resultSet.next();
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a doctor from the database based on their ID.
     *
     * @param doctorID The ID of the doctor to remove.
     * @return true if the doctor was removed successfully, false otherwise.
     */
    public static boolean removeDoctor(int doctorID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DOCTOR_QUERY)) {

            preparedStatement.setInt(1, doctorID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public List<Pair<String, Integer>> getDoctorNamesAndIDs() {
        List<Pair<String, Integer>> doctorData = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DOCTORS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String doctorName = resultSet.getString("Name");
                int doctorID = resultSet.getInt("DoctorID");

                doctorData.add(new Pair<>(doctorName, doctorID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorData;
    }
    /**
     * Removes a patient from the database based on their name.
     *
     * @param patientName The name of the patient to remove.
     * @return true if the patient was removed successfully, false otherwise.
     */
    public boolean removePatient(String patientName) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PATIENT_QUERY)) {

            preparedStatement.setString(1, patientName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the list of patient names from the database.
     *
     * @return A list of patient names.
     */
    public List<String> getPatientNames() {
        List<String> patientNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_PATIENT_NAMES_QUERY)) {

            while (resultSet.next()) {
                patientNames.add(resultSet.getString("first_name")); // Add each name to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientNames;
    }

    /**
     * Removes a staff member from the database based on their name.
     *
     * @param staffName The name of the staff member to remove.
     * @return true if the staff member was removed successfully, false otherwise.
     */
    public boolean removeStaff(String staffName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_QUERY)) {

            preparedStatement.setString(1, staffName);  // Using staff name to remove
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Retrieves the details of an admin from the database based on their username.
     *
     * @param username The username of the admin.
     * @return An Admin object containing the admin's details, or null if not found.
     */
    public Admin getAdminDetails(String username) {
        String query = "SELECT * FROM admins WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFirstName(resultSet.getString("firstName"));
                admin.setLastName(resultSet.getString("lastName"));
                admin.setContactNo(resultSet.getString("contactNo"));
                admin.setDob(resultSet.getString("dob"));
                admin.setAddress(resultSet.getString("address"));
                return admin;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getStaffNames() {
        List<String> staffNames = new ArrayList<>();
        String query = "SELECT first_name FROM staff"; // SQL query to get first names of staff members

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Iterate through the result set and add staff names to the list
            while (resultSet.next()) {
                staffNames.add(resultSet.getString("first_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return staffNames; // Return the list of staff names
    }

}
