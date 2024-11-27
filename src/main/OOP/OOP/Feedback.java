package OOP;

import Database.Feedback_Handler;

import java.util.List;

public class Feedback {

    private int id;
    private String patientName;
    private String doctorName;
    private String hospitalName;
    private String experienceRating;
    private String recommendations;
    private String feedbackComments;

    // Feedback_Handler instance to interact with database
    private static Feedback_Handler feedbackHandler = new Feedback_Handler();

    // Default constructor
    public Feedback() {}

    // Constructor to initialize all fields
    public Feedback(int id, String patientName, String doctorName, String hospitalName, String experienceRating, String recommendations, String feedbackComments) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.hospitalName = hospitalName;
        this.experienceRating = experienceRating;
        this.recommendations = recommendations;
        this.feedbackComments = feedbackComments;
    }

    public static List<Feedback> getFeedbackByDoctorName(String doctorName) {
        return feedbackHandler.getAllFeedback(doctorName);
    }

    // Getters for each field
    public int getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getExperienceRating() {
        return experienceRating;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public String getFeedbackComments() {
        return feedbackComments;
    }

    // Method to insert feedback into the database (non-static)
    public static boolean insertFeedback(int patientId, String patientName, String doctorName, String hospitalName,
                                         String experienceRating, String recommendations, String feedbackComments) {
        // Validate the data (you can add more validation logic here)
        if (patientName.isEmpty() || doctorName.isEmpty() || hospitalName.isEmpty()) {
            return false;  // Invalid input, return false
        }

        // Pass the data to the Feedback_Handler to save to the database
        return feedbackHandler.insertFeedback(patientName, doctorName, hospitalName, experienceRating, recommendations, feedbackComments);
    }
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", experienceRating=" + experienceRating +
                ", recommendations='" + recommendations + '\'' +
                ", feedbackComments='" + feedbackComments + '\'' +
                '}';
    }



}
