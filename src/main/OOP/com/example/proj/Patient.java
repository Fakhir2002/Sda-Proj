package com.example.proj;

import com.example.temp.DB_HANDLER.Patient_Handler;
import java.time.LocalDate;
import java.util.List;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob; // LocalDate for date of birth
    private String address;
    private String username;
    private String password;
    private static Patient_Handler patientHandler = new Patient_Handler();

    // Constructor to initialize patient data by username
    public Patient(String username) {
        this.username = username;
        updatePatientDetails();  // Update details when the Patient object is created
    }

    // Constructor to initialize all patient data (id, first name, last name, etc.)
    public Patient(int id, String firstName, String lastName, String contactNo, String dob, String address, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    // Method to fetch and update patient details from the database
    public void updatePatientDetails() {
        Patient_Handler handler = new Patient_Handler();
        Patient patientDetails = handler.getPatientDetails(this.username);

        // If patient details are found, update the fields
        if (patientDetails != null) {
            this.id = patientDetails.getId();
            this.firstName = patientDetails.getFirstName();
            this.lastName = patientDetails.getLastName();
            this.contactNo = patientDetails.getContactNo();
            this.dob = patientDetails.getDob();
            this.address = patientDetails.getAddress();
            this.password = patientDetails.getPassword();
        } else {
            System.out.println("No patient found with the username: " + this.username);
        }
    }

    // Getter methods for patient data
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNo() { return contactNo; }
    public String getDob() { return dob; }  // LocalDate for date of birth
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Optional: Setter methods if needed
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public void setDob(String dob) { this.dob = dob; }
    public void setAddress(String address) { this.address = address; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public static boolean registerPatient(String firstName, String lastName, String contactNo,
                                          String dob, String address, String username, String password) {
        return patientHandler.registerPatient(firstName, lastName, contactNo, dob, address, username, password);
    }

    // Method to validate a patient's login (calls the DB Handler's login validation method)
    public static boolean validateLogin(String username, String password) {
        return patientHandler.validateLogin(username, password);
    }

    // Method to retrieve patient details by username (calls the DB Handler's query method)
    public static Patient getPatientDetails(String username) {
        return patientHandler.getPatientDetails(username);
    }

    // Method to retrieve all patients (calls the DB Handler's get all patients method)
    public static List<Patient> getAllPatients() {
        return patientHandler.getAllPatientDetails();
    }
}
