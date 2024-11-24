package com.example.proj;

import com.example.temp.DB_HANDLER.StaffRegister_Handler;

import java.util.List;

public class Staff {
    private int id; // Unique identifier for the staff
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;
    private String username;
    private String passwordHash;
    private String hospital;

    // Constructor to initialize Staff with username (used to fetch details)
    public Staff(String username) {
        this.username = username;
        updateStaffDetails(); // Fetch staff details when the object is created
    }

    // Constructor to initialize Staff with all details (for creation)
    public Staff(int id, String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String passwordHash, String hospital) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.passwordHash = passwordHash;
        this.hospital = hospital;
    }

    // Method to fetch and update staff details from the database
    public void updateStaffDetails() {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        Staff staffDetails = handler.getStaffDetails(this.username);

        // If staff details are found, update the fields
        if (staffDetails != null) {
            this.id = staffDetails.getId(); // Set the staff's ID
            this.firstName = staffDetails.getFirstName();
            this.lastName = staffDetails.getLastName();
            this.contactNo = staffDetails.getContactNo();
            this.dob = staffDetails.getDob();
            this.address = staffDetails.getAddress();
            this.passwordHash = staffDetails.getPasswordHash();
            this.hospital = staffDetails.getHospital();
        } else {
            System.out.println("No staff found with the username: " + this.username);
        }
    }

    // Getter methods for staff data
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getHospital() {
        return hospital;
    }

    // Optional: Override the toString method for better logging/debugging
    @Override
    public String toString() {
        return "Staff{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', hospital='" + hospital + "'}";
    }

    // Static methods for common operations
    public static boolean registerStaff(String firstName, String lastName, String contactNo,
                                        String dob, String address, String username,
                                        String passwordHash, String hospital) {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.registerStaff(firstName, lastName, contactNo, dob, address, username, passwordHash, hospital);
    }

    public static boolean validateLogin(String username, String password) {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.validateLogin(username, password);
    }

    public static List<String> getAllStaff() {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.getAllStaff();
    }

    public static List<Staff> getStaffByHospital(String hospitalName) {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.getStaffByHospital(hospitalName);
    }

    public static Staff getStaffByName(String firstName, String lastName) {
        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.getStaffByName(firstName, lastName);
    }
}
