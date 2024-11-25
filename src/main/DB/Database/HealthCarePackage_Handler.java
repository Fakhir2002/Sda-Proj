package Database;

import com.example.proj.HealthCarePackages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthCarePackage_Handler implements DatabaseConfig{


    // SQL queries
    private static final String INSERT_PACKAGE_QUERY =
            "INSERT INTO healthcare_packages (name, hospital_name, start_date, end_date, price, description) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_ALL_PACKAGES_QUERY =
            "SELECT * FROM healthcare_packages";

    private static final String GET_PACKAGE_BY_HOSPITAL_QUERY =
            "SELECT * FROM healthcare_packages WHERE hospital_name = ?";

    private static final String DELETE_PACKAGE_QUERY =
            "DELETE FROM healthcare_packages WHERE packageID = ?";

    /**
     * Adds a new healthcare package to the database.
     *
     * @param healthCarePackage The HealthCarePackages object to add.
     * @return true if the package was added successfully, false otherwise.
     */
    public boolean addPackage(HealthCarePackages healthCarePackage) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PACKAGE_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, healthCarePackage.getName());
            preparedStatement.setString(2, healthCarePackage.getHospitalName());
            preparedStatement.setDate(3, Date.valueOf(healthCarePackage.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(healthCarePackage.getEndDate()));
            preparedStatement.setDouble(5, healthCarePackage.getPrice());
            preparedStatement.setString(6, healthCarePackage.getDescription());

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all healthcare packages from the database.
     *
     * @return A list of HealthCarePackages objects.
     */
    public List<HealthCarePackages> getAllPackages() {
        List<HealthCarePackages> packages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PACKAGES_QUERY)) {

            while (resultSet.next()) {
                // Create a new HealthCarePackages object from the result set
                HealthCarePackages healthCarePackage = new HealthCarePackages(
                        resultSet.getString("name"),
                        resultSet.getString("hospital_name"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getDouble("price"),
                        resultSet.getString("description")
                );
                packages.add(healthCarePackage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    /**
     * Retrieves healthcare packages for a specific hospital.
     *
     * @param hospitalName The hospital name to filter packages by.
     * @return A list of HealthCarePackages objects.
     */
    public List<HealthCarePackages> getPackagesByHospital(String hospitalName) {
        List<HealthCarePackages> packages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PACKAGE_BY_HOSPITAL_QUERY)) {

            // Set the hospital name parameter
            preparedStatement.setString(1, hospitalName);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HealthCarePackages healthCarePackage = new HealthCarePackages(
                            resultSet.getString("name"),
                            resultSet.getString("hospital_name"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate(),
                            resultSet.getDouble("price"),
                            resultSet.getString("description")
                    );
                    packages.add(healthCarePackage);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    /**
     * Deletes a healthcare package by its ID.
     *
     * @param packageID The ID of the package to delete.
     * @return true if the package was deleted successfully, false otherwise.
     */
    public boolean deletePackage(int packageID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PACKAGE_QUERY)) {

            // Set the package ID parameter
            preparedStatement.setInt(1, packageID);

            // Execute the query
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
