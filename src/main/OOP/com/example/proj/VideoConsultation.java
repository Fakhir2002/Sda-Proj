package com.example.proj;

import com.example.temp.DB_HANDLER.VideoConsultationDBHandler;

import java.util.List;

public class VideoConsultation {

    // Instance variables
    private int consultationId;
    private String status;
    private int patientId;
    private int doctorId;
    private VideoConsultationDBHandler videoConsultationDBHandler;

    // Constructor to initialize the video consultation
    public VideoConsultation(int consultationId, String status, int patientId, int doctorId) {
        this.consultationId = consultationId;
        this.status = status;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public VideoConsultation() {
        videoConsultationDBHandler = new VideoConsultationDBHandler();
    }

    // Getter and Setter methods for each field

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    // Optionally, you can override toString() to print the details of the consultation
    @Override
    public String toString() {
        return "VideoConsultation{" +
                "consultationId=" + consultationId +
                ", status='" + status + '\'' +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                '}';
    }

    // Method to save this consultation to the database
    public static boolean saveConsultationToDB(String status, int patientId, int doctorId) {
        // Call the static method in VideoConsultationDBHandler to save this consultation
        return VideoConsultationDBHandler.saveVideoConsultation(status, patientId, doctorId);
    }

    public List<Integer> getConsultationsForDoctor(int doctorId) {
        return videoConsultationDBHandler.getConsultationsForDoctor(doctorId);
    }
}
