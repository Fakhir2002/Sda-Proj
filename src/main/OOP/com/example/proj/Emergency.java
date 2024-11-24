package com.example.proj;

import com.example.temp.DB_HANDLER.Emergency_Handler;
import java.util.List;

public class Emergency {

    private int emergency_id;
    private int patient_id;
    private int hospital_id;
    private String type;
    private String status;
    private String description;

    private Emergency_Handler emergencyHandler;  // Declare emergencyHandler

    // Default constructor
    public Emergency() {
        this.emergencyHandler = new Emergency_Handler();  // Ensure initialization here
    }

    // Parameterized constructor
    public Emergency(int patient_id, int hospital_id, String type, String status, String description) {
        this.patient_id = patient_id;
        this.hospital_id = hospital_id;
        this.type = type;
        this.status = status;
        this.description = description;
        this.emergencyHandler = new Emergency_Handler();  // Initialize the emergencyHandler
    }
    public boolean insertEmergency(int patientId, int hospital_id, String type, String status, String description){
      return  emergencyHandler.insertEmergency(patientId,hospital_id,type,status,description);

    }

    // Method to call the getHospitalNames method from Emergency_Handler

    // Getters and Setters
    public int getEmergency_id() {
        return emergency_id;
    }

    public void setEmergency_id(int emergency_id) {
        this.emergency_id = emergency_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int gethospital_id() {
        return hospital_id;
    }

    public void sethospital_id(int doctor_id) {
        this.hospital_id = doctor_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> fetchHospitalNames() {
        if (emergencyHandler == null) {
            emergencyHandler = new Emergency_Handler();  // Lazy initialization in case it's not already initialized
        }
        return emergencyHandler.getHospitalNames();  // This should no longer throw NullPointerException
    }
}
