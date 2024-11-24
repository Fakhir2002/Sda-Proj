package com.example.proj;

import com.example.temp.DB_HANDLER.StaffRegister_Handler;

public class Staff {
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;
    private String username;
    private String password;
    private String hospital; // New hospital field

    // Constructor to initialize staff details
    public Staff(String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String password, String hospital) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
        this.hospital = hospital; // Initialize hospital field
    }



    // Getter methods for staff data
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHospital() { // Getter for hospital
        return hospital;
    }

    public static boolean registerStaff(String firstName, String lastName, String contactNo,
                                        String dob, String address, String username,
                                        String password, String hospital)
    {

        StaffRegister_Handler handler = new StaffRegister_Handler();
        return handler.registerStaff(firstName, lastName, contactNo, dob, address, username, password, hospital);
    }




    // Method to register the staff by calling the DB handler
}
