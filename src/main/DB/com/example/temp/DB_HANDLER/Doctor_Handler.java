package com.example.temp.DB_HANDLER;

import com.example.proj.Doctor;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Doctor_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Replace 'user' with your database name
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting doctor data
    private static final String INSERT_DOCTOR_QUERY =
            "INSERT INTO doctors (name, dob, hospital, specialty, contact, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // SQL query for verifying doctor login credentials
    private static final String LOGIN_QUERY =
            "SELECT * FROM doctors WHERE username = ? AND password = ?";

    // SQL query for getting all doctors
    private static final String GET_ALL_DOCTORS_QUERY =
            "SELECT name FROM doctors"; // Query to get all doctor names from the database

    // SQL query for getting all specialties
    private static final String GET_ALL_SPECIALTIES_QUERY =
            "SELECT DISTINCT specialty FROM doctors"; // Query to get all unique specialties from the database

    // SQL query for retrieving doctor details by username
    private static final String GET_DOCTOR_DETAILS_QUERY =
            "SELECT * FROM doctors WHERE username = ?"; // Query to get doctor details based on the username

    /**
     * Saves doctor data to the database.
     *
     * @param Name        Doctor's full name
     * @param DOB         Doctor's date of birth
     * @param Hospital    Doctor's affiliated hospital
     * @param Specialty   Doctor's specialty
     * @param Contact     Doctor's contact number
     * @param Address     Doctor's address
     * @param Username    Doctor's username
     * @param PasswordHash Doctor's password hash
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerDoctor(String Name, String DOB, String Hospital, String Specialty,
                                  String Contact, String Address, String Username, String PasswordHash) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, DOB); // Ensure DOB is in 'YYYY-MM-DD' format
            preparedStatement.setString(3, Hospital);
            preparedStatement.setString(4, Specialty);
            preparedStatement.setString(5, Contact);
            preparedStatement.setString(6, Address);
            preparedStatement.setString(7, Username);
            preparedStatement.setString(8, PasswordHash); // Consider hashing the password for security

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
     * Retrieves a list of all doctors.
     *
     * @return A list of doctor names.
     */
    public List<String> getAllDoctors() {
        List<String> doctors = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_DOCTORS_QUERY)) {

            while (resultSet.next()) {
                String doctorName = resultSet.getString("name");
                doctors.add(doctorName); // Add the doctor name to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    /**
     * Retrieves a list of all specialties.
     *
     * @return A list of specialties.
     */
    public List<String> getAllSpecialities() {
        List<String> specialities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SPECIALTIES_QUERY)) {

            while (resultSet.next()) {
                String specialty = resultSet.getString("specialty");
                specialities.add(specialty); // Add the specialty to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialities;
    }

    /**
     * Retrieves doctor details based on username.
     *
     * @param username Doctor's username.
     * @return A Doctor object containing all details or null if not found.
     */
    public Doctor getDoctorDetails(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DOCTOR_DETAILS_QUERY)) {

            // Set the username parameter for the prepared statement
            preparedStatement.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch doctor details
                    int id = resultSet.getInt("DoctorID");
                    String name = resultSet.getString("name");
                    String dob = resultSet.getString("dob");
                    String hospital = resultSet.getString("hospital");
                    String specialty = resultSet.getString("specialty");
                    String contact = resultSet.getString("contact");
                    String address = resultSet.getString("address");
                    String passwordHash = resultSet.getString("password");

                    // Create and return a Doctor object with the fetched details
                    return new Doctor(id,name, dob, hospital, specialty, contact, address, username, passwordHash);
                } else {
                    return null; // No doctor found with the given username
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Doctor> getDoctorsByHospital(String hospitalName) {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors WHERE hospital = ?"; // SQL query to get doctors for the specific hospital

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the hospital name in the query
            preparedStatement.setString(1, hospitalName);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Iterate through the result set and create Doctor objects
                while (resultSet.next()) {
                    int id = resultSet.getInt("DoctorID");
                    String name = resultSet.getString("name");
                    String dob = resultSet.getString("dob");
                    String hospital = resultSet.getString("hospital");
                    String specialty = resultSet.getString("specialty");
                    String contact = resultSet.getString("contact");
                    String address = resultSet.getString("address");
                    String username = resultSet.getString("username");
                    String passwordHash = resultSet.getString("password");

                    // Create a new Doctor object
                    Doctor doctor = new Doctor(id, name, dob, hospital, specialty, contact, address, username, passwordHash);
                    doctors.add(doctor);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
    public Doctor getDoctorByName(String name) {
        String query = "SELECT * FROM doctors WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Doctor(
                            resultSet.getInt("DoctorID"),
                            resultSet.getString("Name"),
                            resultSet.getString("DOB"),
                            resultSet.getString("Hospital"),
                            resultSet.getString("Specialty"),
                            resultSet.getString("Contact"),
                            resultSet.getString("Address"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





    public List<Doctor> getAllDoctorsDetails() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors"; // SQL query to get all doctor details

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Iterate through the result set and create Doctor objects
            while (resultSet.next()) {
                int id = resultSet.getInt("DoctorID");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String hospital = resultSet.getString("hospital");
                String specialty = resultSet.getString("specialty");
                String contact = resultSet.getString("contact");
                String address = resultSet.getString("address");
                String username = resultSet.getString("username");
                String passwordHash = resultSet.getString("password");

                // Create a new Doctor object
                Doctor doctor = new Doctor(id,name, dob, hospital, specialty, contact, address, username, passwordHash);
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Debugging: Print all doctors in the list before returning
        System.out.println("List of doctors:");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);  // Assuming your Doctor class has a meaningful toString() method
        }

        return doctors;

    }




}
