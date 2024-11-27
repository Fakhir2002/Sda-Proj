package OOP;

import Database.Admin_Handler;

public class Admin extends User {
    private String firstName;
    private String lastName;
    private String contactNo;
    private String dob;
    private String address;

    private static final Admin_Handler adminHandler = new Admin_Handler();

    public Admin(String username, String password) {
        super(username, password);
        updateDetails();
    }
    public Admin(){

    }

    public Admin(String firstName, String lastName, String contactNo, String dob,
                 String address, String username, String password) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.dob = dob;
        this.address = address;
    }
    public boolean login() {
        // Create an instance of AdminLogin_Handler
        Admin_Handler dbHandler = new Admin_Handler();

        // Call the validateLogin method and return the result
        return dbHandler.validateLogin(username, password);
    }



    @Override
    public void updateDetails() {
        Admin adminDetails = adminHandler.getAdminDetails(this.username);
        if (adminDetails != null) {
            this.firstName = adminDetails.getFirstName();
            this.lastName = adminDetails.getLastName();
            this.contactNo = adminDetails.getContactNo();
            this.dob = adminDetails.getDob();
            this.address = adminDetails.getAddress();
            this.password = adminDetails.getPassword();
        } else {
            System.out.println("No admin found with the username: " + this.username);
        }
    }

    public  static boolean validateLogin(String username, String password) {
        return adminHandler.validateLogin(username, password);
    }

    public static boolean register(String firstName, String lastName, String contactNo, String dob,
                                   String address, String username, String password) {
        return adminHandler.registerAdmin(firstName, lastName, contactNo, dob, address, username, password);
    }

    // Static methods for admin operations
    public static boolean removeDoctor(int doctorID) {
        return adminHandler.removeDoctor(doctorID);
    }

    public static boolean removePatient(String patientName) {
        return adminHandler.removePatient(patientName);
    }

    public static boolean removeStaff(String staffName) {
        return adminHandler.removeStaff(staffName);
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



    public void setFirstName(String firstName) {
        this.firstName= firstName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo=contactNo;
    }

    public void setDob(String dob) {
        this.dob=dob;
    }

    public void setAddress(String address) {
        this.address=address;
    }
}
