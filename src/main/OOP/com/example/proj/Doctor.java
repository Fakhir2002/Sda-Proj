package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;

import java.util.List;

public class Doctor extends User {
    private int id;
    private String name;
    private String dob;
    private String hospital;
    private String specialty;
    private String contact;
    private String address;


    private static final Doctor_Handler doctorHandler = new Doctor_Handler();

    public Doctor(String username, String password) {
        super(username, password);
        updateDetails();
    }

    public Doctor(String username){
        this.username= username;
        updateDetails();
    }

    public Doctor(){

    }

    public Doctor(int id, String name, String dob, String hospital, String specialty,
                  String contact, String address, String username, String passwordHash) {
        super(username, passwordHash);
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.hospital = hospital;
        this.specialty = specialty;
        this.contact = contact;
        this.address = address;
    }

    @Override
    public void updateDetails() {
        Doctor doctorDetails = doctorHandler.getDoctorDetails(this.username);
        if (doctorDetails != null) {
            this.id = doctorDetails.getId();
            this.name = doctorDetails.getName();
            this.dob = doctorDetails.getDob();
            this.hospital = doctorDetails.getHospital();
            this.specialty = doctorDetails.getSpecialty();
            this.contact = doctorDetails.getContact();
            this.address = doctorDetails.getAddress();
            this.password = doctorDetails.getPasswordHash();
        }
    }

    // Static methods remain unchanged
    public static boolean registerDoctor(String name, String dob, String hospital, String specialty,
                                         String contact, String address, String username, String passwordHash) {
        return doctorHandler.registerDoctor(name, dob, hospital, specialty, contact, address, username, passwordHash);
    }

    public static boolean validateLogin(String username, String password) {
        return doctorHandler.validateLogin(username, password);
    }

    public static List<String> getAllDoctors() {
        return doctorHandler.getAllDoctors();
    }

    public static List<Doctor> getDoctorsByHospital(String hospitalName) {
        return doctorHandler.getDoctorsByHospital(hospitalName);
    }

    public static Doctor getDoctorbyName(String name) {
        return doctorHandler.getDoctorByName(name);
    }




    // Getter methods for doctor data
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getHospital() { return hospital; }
    public String getSpecialty() { return specialty; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return password; }

}
