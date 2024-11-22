package com.example.proj;

import com.example.temp.DB_HANDLER.PatientRegister_Handler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Patient {
    private String firstName;
    private String lastName;
    private String contactNo;
    private LocalDate dob;
    private String address;
    private String username;
    private String password;

    // Constructor to initialize patient data
    public Patient(String firstName, String lastName, String contactNo, LocalDate dob,
                   String address, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    // Getter methods for patient data
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNo() { return contactNo; }
    public LocalDate getDob() { return dob; }
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Register the patient by calling the DB handler
    public boolean register() {
        // Convert LocalDate to String
        String dobString = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        PatientRegister_Handler handler = new PatientRegister_Handler();
        return handler.registerPatient(firstName, lastName, contactNo, dobString, address, username, password);
    }
}
