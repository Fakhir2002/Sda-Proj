package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;

public class Doctor {
    private String name;
    private String dob;
    private String hospital;
    private String specialty;
    private String contact;
    private String address;
    private String username;
    private String passwordHash;

    // Constructor to initialize Doctor with username (used to fetch details)
    public Doctor(String username) {
        this.username = username;
        updateDoctorDetails();  // Fetch doctor details when the object is created
    }

    // Constructor to initialize Doctor with all details (for creation)
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

    // Method to fetch and update doctor details from the database
    public void updateDoctorDetails() {
        Doctor_Handler handler = new Doctor_Handler();
        Doctor doctorDetails = handler.getDoctorDetails(this.username);

        // If doctor details are found, update the fields
        if (doctorDetails != null) {
            this.name = doctorDetails.getName();
            this.dob = doctorDetails.getDob();
            this.hospital = doctorDetails.getHospital();
            this.specialty = doctorDetails.getSpecialty();
            this.contact = doctorDetails.getContact();
            this.address = doctorDetails.getAddress();
            this.passwordHash = doctorDetails.getPasswordHash();
        } else {
            System.out.println("No doctor found with the username: " + this.username);
        }
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

    // Optional: Override the toString method for better logging/debugging
    @Override
    public String toString() {
        return "Doctor{name='" + name + "', specialty='" + specialty + "', hospital='" + hospital + "'}";
    }
}
