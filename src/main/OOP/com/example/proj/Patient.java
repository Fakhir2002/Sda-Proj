package com.example.proj;

import com.example.temp.DB_HANDLER.Patient_Handler;

import java.util.List;

public class Patient extends User {
    private int id;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;

    private static final Patient_Handler patientHandler = new Patient_Handler();

    public Patient(String username, String password) {
        super(username, password);
        updateDetails();
    }
    public Patient(String username){
        this.username= username;
        updateDetails();
    }
    public Patient(){

    }

    public Patient(int id, String firstName, String lastName, String contactNo, String dob,
                   String address, String username, String password) {
        super(username, password);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
    }

    @Override
    public void updateDetails() {
        Patient patientDetails = patientHandler.getPatientDetails(this.username);
        if (patientDetails != null) {
            this.id = patientDetails.getId();
            this.firstName = patientDetails.getFirstName();
            this.lastName = patientDetails.getLastName();
            this.contactNo = patientDetails.getContactNo();
            this.dob = patientDetails.getDob();
            this.address = patientDetails.getAddress();
            this.password = patientDetails.getPassword();
        }
    }

    // Static methods remain unchanged
    public static boolean registerPatient(String firstName, String lastName, String contactNo,
                                          String dob, String address, String username, String password) {
        return patientHandler.registerPatient(firstName, lastName, contactNo, dob, address, username, password);
    }

    public static boolean validateLogin(String username, String password) {
        return patientHandler.validateLogin(username, password);
    }

    public static List<Patient> getAllPatients() {
        return patientHandler.getAllPatientDetails();
    }

    public String getPatientNameById(int patientId) {
        return patientHandler.getPatientNameById(patientId);
    }

    public String getPatientUsernameByName(String selectedPatientName) {
        return patientHandler.getPatientUsernameByName(selectedPatientName);
    }



    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNo() { return contactNo; }
    public String getDob() { return dob; }




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
