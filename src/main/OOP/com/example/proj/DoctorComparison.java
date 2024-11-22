package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;
import java.util.List;

public class DoctorComparison {

    private List<Doctor> doctors;

    public DoctorComparison() {
        // Initialize the handler and fetch the doctor data
        Doctor_Handler doctorHandler = new Doctor_Handler();
        this.doctors = doctorHandler.getAllDoctorsDetails();  // Assuming this returns a list of Doctor objects
    }

    public String compareDoctors(String doctorName1, String doctorName2) {
        // Simply compare the names of the doctors
        if (doctorName1 != null && doctorName2 != null) {
            if (doctorName1.equals(doctorName2)) {
                return "Both doctors have the same name.";
            } else {
                return "The doctors have different names.";
            }
        }
        return "Comparison failed. One or both doctor names are invalid.";
    }
}
