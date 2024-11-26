package com.example.proj;

import Database.Appointment_Handler;
import Database.Hospital_Handler;
import Database.Doctor_Handler;
import Database.ManageAppointment_Handler;
import javafx.collections.ObservableList;

import java.util.List;

public class Appointment {

    private List<Hospital> hospitals;
    private List<Doctor> doctors;
    private List<String> specialities;
    static Appointment_Handler handler = new Appointment_Handler();
    ManageAppointment_Handler manageAppointmentHandler = new ManageAppointment_Handler();



    public Appointment() {
        // Initialize the handlers
        Hospital_Handler hospitalHandler = new Hospital_Handler();
        Doctor_Handler doctorHandler = new Doctor_Handler();

        // Get the lists from the DB handler classes
        this.hospitals = hospitalHandler.getAllHospitals();  // Returns a list of Hospital objects
        this.doctors = doctorHandler.getAllDoctorsDetails();  // Returns a list of Doctor objects
        this.specialities = doctorHandler.getAllSpecialities();  // Assuming specialities are just strings
    }

    public static List<String> getBookedTimeSlots(int doctorId, String selectedDate) {
        return handler.getBookedTimeSlots(doctorId,selectedDate);
    }

    // Getters and setters
    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public static boolean deleteAppointment(int appointmentId) {
        // Create an instance of the Appointment_Handler
        Appointment_Handler handler = new Appointment_Handler();

        // Call the deleteAppointment method from the handler and return its result
        return handler.deleteAppointment(appointmentId);
    }

    public static boolean saveAppointment(String status, String date, String time, int doctorId, int patientId){

        return handler.saveAppointment(status, date, time, doctorId, patientId);
    }

    public ObservableList<Object[]> getAppointments(int doctorId) {
        return manageAppointmentHandler.getAppointments(doctorId);
    }

    public boolean updateAppointmentStatus(int appointmentId) {

        return  manageAppointmentHandler.updateAppointmentStatus(appointmentId);
    }

    public ObservableList<Object[]> getPendingAppointments(int doctorId) {
        return handler.getPendingAppointments(doctorId);  // Call the method in Appointment_Handler to fetch pending appointments
    }
    public int getPatientIdByAppointmentId(int appointmentId){
        return manageAppointmentHandler.getPatientIdByAppointmentId(appointmentId);
    }


}
