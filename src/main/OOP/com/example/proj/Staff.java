package com.example.proj;

import com.example.temp.DB_HANDLER.Staff_Handler;

import java.util.List;

public class Staff extends User {
    private int id;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;
    private String passwordHash;
    private String hospital;

    private static final Staff_Handler staffHandler = new Staff_Handler();

    public Staff(String username, String password) {
        super(username, password);
        updateDetails();
    }
    public Staff(String username){
        this.username=username;
        updateDetails();
    }
    public Staff(){

    }

    public Staff(int id, String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String passwordHash, String hospital) {
        super(username, passwordHash);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.hospital = hospital;
    }

    @Override
    public void updateDetails() {
        Staff staffDetails = staffHandler.getStaffDetails(this.username);
        if (staffDetails != null) {
            this.id = staffDetails.getId();
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

    // Static methods for staff operations
    public static boolean registerStaff(String firstName, String lastName, String contactNo,
                                        String dob, String address, String username,
                                        String passwordHash, String hospital) {
        return staffHandler.registerStaff(firstName, lastName, contactNo, dob, address, username, passwordHash, hospital);
    }

    public static boolean validateLogin(String username, String password) {
        return staffHandler.validateLogin(username, password);
    }

    public static List<String> getAllStaff() {
        return staffHandler.getAllStaff();
    }

    public static List<Staff> getStaffByHospital(String hospitalName) {
        return staffHandler.getStaffByHospital(hospitalName);
    }

    public static Staff getStaffByName(String firstName, String lastName) {
        return staffHandler.getStaffByName(firstName, lastName);
    }

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getHospital() {
        return hospital;
    }
}
