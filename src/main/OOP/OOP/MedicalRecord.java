package OOP;

import Database.MedicalHistory_Handler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class MedicalRecord {
    private int historyID; // Unique ID for the record
    private SimpleStringProperty symptoms; // Symptoms reported by the patient
    private SimpleStringProperty diagnosis; // Diagnosis by the doctor
    private SimpleStringProperty treatment; // Treatment prescribed by the doctor
    private SimpleStringProperty date; // Date of the record
    private SimpleBooleanProperty isUpdated; // Indicates if the record is updated

    private static final MedicalHistory_Handler dbHandler = new MedicalHistory_Handler(); // Database handler instance

    // Constructor to initialize all fields
    public MedicalRecord(int historyID, String symptoms, String diagnosis, String treatment, String date, boolean isUpdated) {
        this.historyID = historyID;
        this.symptoms = new SimpleStringProperty(symptoms);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.treatment = new SimpleStringProperty(treatment);
        this.date = new SimpleStringProperty(date);
        this.isUpdated = new SimpleBooleanProperty(isUpdated);
    }

    // Overloaded constructor for simpler use cases
    public MedicalRecord(String symptoms, String date) {
        this.symptoms = new SimpleStringProperty(symptoms);
        this.date = new SimpleStringProperty(date);
        this.diagnosis = new SimpleStringProperty("");
        this.treatment = new SimpleStringProperty("");
        this.isUpdated = new SimpleBooleanProperty(false);
    }

    // Default constructor
    public MedicalRecord() {
    }

    public static List<MedicalRecord> getReport(int id) {
        return dbHandler.getReport(id);
    }

    // Getters and setters for historyID
    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    // Getter and property methods for Symptoms
    public String getSymptoms() {
        return symptoms.get();
    }

    public void setSymptoms(String symptoms) {
        this.symptoms.set(symptoms);
    }

    public ObservableValue<String> symptomsProperty() {
        return symptoms;
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

    // Getter and property methods for isUpdated
    public boolean isUpdated() {
        return isUpdated.get();
    }

    public void setUpdated(boolean isUpdated) {
        this.isUpdated.set(isUpdated);
    }

    public ObservableValue<Boolean> isUpdatedProperty() {
        return isUpdated;
    }

    // Retrieve medical history for a specific patient
    public static List<MedicalRecord> getMedicalHistory(int patientId) {
        return dbHandler.getMedicalHistory(patientId);
    }

    // Save a new medical history record (initially without diagnosis and treatment)
    public boolean saveMedicalHistory(String symptoms, int patientId, int doctorId) {
        return dbHandler.saveMedicalHistory(symptoms, patientId,doctorId);
    }

    // Update an existing medical history record
    public boolean updateMedicalHistory(int historyID, String diagnosis, String treatment, String date) {
        return dbHandler.updateMedicalHistory(historyID, diagnosis, treatment,date);
    }

    public List<Integer> getConsultationsForDoctor(int doctorId) {
        return dbHandler.getConsultationsForDoctor(doctorId);
    }

    public boolean hasExistingVideoConsultation(int id, int doctorId) {
        return dbHandler.hasExistingVideoConsultation(id,doctorId);
    }
}
