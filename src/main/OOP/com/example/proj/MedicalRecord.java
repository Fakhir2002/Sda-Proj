package com.example.proj;

import com.example.temp.DB_HANDLER.MedicalHistory_Handler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class MedicalRecord {
    private final SimpleStringProperty diagnosis;
    private final SimpleStringProperty treatment;
    private final SimpleStringProperty date;

    private final MedicalHistory_Handler dbHandler = new MedicalHistory_Handler(); // Database handler instance

    // Constructor to initialize the fields
    public MedicalRecord(String diagnosis, String treatment, String date) {
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.treatment = new SimpleStringProperty(treatment);
        this.date = new SimpleStringProperty(date);
    }

    // Getter and property methods for Diagnosis
    public String getDiagnosis() {
        return diagnosis.get();
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public ObservableValue<String> diagnosisProperty() {
        return diagnosis;
    }

    // Getter and property methods for Treatment
    public String getTreatment() {
        return treatment.get();
    }

    public void setTreatment(String treatment) {
        this.treatment.set(treatment);
    }

    public ObservableValue<String> treatmentProperty() {
        return treatment;
    }

    // Getter and property methods for Date
    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public ObservableValue<String> dateProperty() {
        return date;
    }

    public List<MedicalRecord> getMedicalHistory(int id) {
        return dbHandler.getMedicalHistory(id);
    }

    public boolean saveMedicalHistory(String diagnosis, String treatment, String currentDate, int id) {
        return dbHandler.saveMedicalHistory(diagnosis, treatment, currentDate, id);
    }
}
