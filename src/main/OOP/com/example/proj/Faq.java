package com.example.proj;

import com.example.temp.DB_HANDLER.Doctor_Handler;
import com.example.temp.DB_HANDLER.Faq_Handler;
import com.example.temp.DB_HANDLER.Patient_Handler;

import java.util.List;
import java.util.stream.Collectors;

public class Faq {
    private int patientID;
    private int doctorID;
    private String question;
    private String answer;
    private List<Patient> patients;
    private List<Doctor> doctors;
    private static Faq_Handler faqHandler;

    public Faq(){

        Doctor_Handler doctorHandler = new Doctor_Handler();
        Patient_Handler patientHandler = new Patient_Handler();
        faqHandler = new Faq_Handler();

        this.doctors = doctorHandler.getAllDoctorsDetails();
        this.patients = patientHandler.getAllPatientDetails();


    }




    // Getters and setters
    public List<Doctor> getAllDoctors() {
        return doctors;
    }
    public List<String> getAllDoctorNames() {
        return getAllDoctors().stream()
                .map(Doctor::getName)  // Assuming `getName` exists in the `Doctor` class
                .collect(Collectors.toList());
    }

    public static List<Faq> getAllFaqs(){
        return faqHandler.getAllFaqs();
    }




    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public int getDoctorID() { return doctorID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }


    public boolean insertFaq(Faq newFaq) {
        return faqHandler.insertFaq(newFaq);
    }
}
