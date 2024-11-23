package com.example.proj;

import com.example.temp.DB_HANDLER.Admin_Handler;

public class Admin {
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;
    private String username;
    private String password;
    private static Admin_Handler adminHandler=new Admin_Handler();

    // Constructor to initialize admin details
    public Admin(String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    // Getter methods for admin data
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNo() { return contactNo; }
    public String getDob() { return dob; }
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public static boolean removeDoctor(int doctorID) {
        return adminHandler.removeDoctor(doctorID);
    }
    public static boolean removePatient(String patientName) {
        // Delegate the call to the AdminHandler
        return adminHandler.removePatient(patientName);
    }
    public static boolean removeStaff(String staffName) {
        // Delegate the call to AdminHandler
        return adminHandler.removeStaff(staffName);
    }



}
