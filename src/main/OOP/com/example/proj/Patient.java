package com.example.proj;

import com.example.temp.DB_HANDLER.Patient_Handler;
import java.time.LocalDate;

public class Patient {
    private String firstName;
    private String lastName;
    private String contactNo;
    private LocalDate dob; // LocalDate for date of birth
    private String address;
    private String username;
    private String password;

    // Constructor to initialize patient data
    public Patient(String username) {
        this.username = username;
        updatePatientDetails();  // Update details when the Patient object is created
    }

    public Patient(String firstName, String lastName, String contactNo, LocalDate dob, String address, String username, String password) {
    }

    // Method to fetch and update patient details from the database
    public void updatePatientDetails() {
        Patient_Handler handler = new Patient_Handler();
        Patient patientDetails = handler.getPatientDetails(this.username);

        // If patient details are found, update the fields
        if (patientDetails != null) {
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
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNo() { return contactNo; }
    public LocalDate getDob() { return dob; }  // LocalDate for date of birth
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
