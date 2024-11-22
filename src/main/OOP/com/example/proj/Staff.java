package com.example.proj;

public class Staff {
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;
    private String username;
    private String password;

    // Constructor to initialize staff details
    public Staff(String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
        this.username = username;
        this.password = password;
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

    // Method to register the staff by calling the DB handler
}
