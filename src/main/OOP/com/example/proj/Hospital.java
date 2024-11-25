package com.example.proj;

import Database.Hospital_Handler;

import java.util.List;

public class Hospital {
    private String id;  // Hospital ID
    private String name;  // Hospital Name
    private Hospital_Handler hospitalHandler;


    // Constructor to initialize hospital data using ID
    public Hospital(String id) {
        this.id = id;
        updateHospitalDetails();  // Fetch hospital details from the database
    }

    // Constructor to initialize all fields
    public Hospital(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hospital() {
        hospitalHandler = new Hospital_Handler();

    }

    public List<Hospital> getAllHospitals(){
        return hospitalHandler.getAllHospitals();
    }



    // Method to fetch and update hospital details from the database
    public void updateHospitalDetails() {
        Hospital_Handler handler = new Hospital_Handler();
        Hospital hospitalDetails = handler.getHospitalDetails(this.id);

        // If hospital details are found, update the fields
        if (hospitalDetails != null) {
            this.name = hospitalDetails.getName();
        } else {
            System.out.println("No hospital found with the ID: " + this.id);
        }
    }

    // Getter methods for hospital data
    public String getId() { return id; }
    public String getName() { return name; }

    // Override toString to display the hospital name in ComboBox
    @Override
    public String toString() {
        return name; // This will be shown in the ComboBox
    }

    public int getHospitalIdByName(String selectedHospital) {
        return hospitalHandler.getHospitalIdByName(selectedHospital);
    }


}
