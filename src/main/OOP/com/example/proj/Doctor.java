package com.example.proj;

import java.util.Date;

public class Doctor {
    private String name;
    private String dob;
    private String hospital;
    private String specialty;
    private String contact;
    private String address;
    private String username;
    private String passwordHash;

    // Constructor to initialize Doctor details
    public Doctor(String name, String dob, String hospital, String specialty,
                  String contact, String address, String username, String passwordHash) {
        this.name = name;
        this.dob = dob;
        this.hospital = hospital;
        this.specialty = specialty;
        this.contact = contact;
        this.address = address;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getter methods for doctor data
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getHospital() { return hospital; }
    public String getSpecialty() { return specialty; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }

    // Method to register the doctor by calling the DB handler
}
