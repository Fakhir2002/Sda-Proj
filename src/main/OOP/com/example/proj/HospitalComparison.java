package com.example.proj;

import com.example.temp.DB_HANDLER.Hospital_Handler;
import java.util.List;

public class HospitalComparison {

    private List<Hospital> hospitals;

    public HospitalComparison() {
        // Initialize the handler and fetch all hospital data
        Hospital_Handler hospitalHandler = new Hospital_Handler();
        this.hospitals = hospitalHandler.getAllHospitals();  // Fetching the list of all hospitals
    }

    /**
     * Compares two hospitals by their names.
     *
     * @param hospitalName1 The name of the first hospital
     * @param hospitalName2 The name of the second hospital
     * @return A message indicating whether the hospitals have the same name or not
     */
    public String compareHospitals(String hospitalName1, String hospitalName2) {
        if (hospitalName1 != null && hospitalName2 != null) {
            // Find the hospitals by name (you may need to modify the logic based on your data)
            Hospital hospital1 = findHospitalByName(hospitalName1);
            Hospital hospital2 = findHospitalByName(hospitalName2);

            if (hospital1 != null && hospital2 != null) {
                if (hospital1.getName().equalsIgnoreCase(hospital2.getName())) {
                    return "Both hospitals have the same name.";
                } else {
                    return "The hospitals have different names.";
                }
            } else {
                return "One or both hospitals not found.";
            }
        }
        return "Comparison failed. One or both hospital names are invalid.";
    }

    /**
     * Helper method to find a hospital by its name.
     *
     * @param hospitalName The name of the hospital
     * @return The Hospital object if found, or null if not found
     */
    private Hospital findHospitalByName(String hospitalName) {
        for (Hospital hospital : hospitals) {
            if (hospital.getName().equalsIgnoreCase(hospitalName)) {
                return hospital;
            }
        }
        return null;  // Return null if no hospital matches the name
    }
}
