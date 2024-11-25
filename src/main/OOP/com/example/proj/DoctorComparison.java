package com.example.proj;

import Database.Doctor_Handler;

import java.util.List;

public class DoctorComparison {

    private String attribute;
    private String doctor1Value;
    private String doctor2Value;
    private Doctor_Handler doctorHandler;

    public DoctorComparison(String attribute, String doctor1Value, String doctor2Value) {
        this.attribute = attribute;
        this.doctor1Value = doctor1Value;
        this.doctor2Value = doctor2Value;
    }

    public DoctorComparison() {
        doctorHandler= new Doctor_Handler();

    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDoctor1Value() {
        return doctor1Value;
    }

    public void setDoctor1Value(String doctor1Value) {
        this.doctor1Value = doctor1Value;
    }

    public String getDoctor2Value() {
        return doctor2Value;
    }

    public void setDoctor2Value(String doctor2Value) {
        this.doctor2Value = doctor2Value;
    }

    public List<String> getAllDoctors() {
        return doctorHandler.getAllDoctors();
    }
}
