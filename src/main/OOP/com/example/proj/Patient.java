package com.example.proj;

import com.example.temp.DB_HANDLER.Patient_Handler;
import java.time.LocalDate;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob; // LocalDate for date of birth
    private String address;
    private String username;
    private String password;

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
}
