package com.example.proj;

import com.example.temp.DB_HANDLER.Feedback_Handler;

public class Feedback {

    private static Feedback_Handler feedbackHandler=new Feedback_Handler();

    public Feedback() {
        // Initialize the feedback handler that interacts with the database
        this.feedbackHandler = new Feedback_Handler();
    }

    /**
     * Collects feedback from the user and saves it to the database.
     *
     * @param patientId          The ID of the patient providing the feedback.
     * @param patientName        The name of the patient providing the feedback.
     * @param doctorName         The name of the doctor being reviewed.
     * @param hospitalName       The name of the hospital where the feedback is provided.
     * @param experienceRating   True for positive feedback ("Yes"), false for negative feedback ("No").
     * @param recommendations    Any suggestions or recommendations from the patient.
     * @param feedbackComments   Additional feedback/comments from the patient.
     * @return true if the feedback is successfully saved, false otherwise.
     */
    public static boolean insertFeedback(int patientId, String patientName, String doctorName, String hospitalName,
                                         boolean experienceRating, String recommendations, String feedbackComments) {

        // Validate the data (you can add more validation logic here)
        if (patientName.isEmpty() || doctorName.isEmpty() || hospitalName.isEmpty()) {
            return false;  // Invalid input, return false
        }

        // Pass the data to the Feedback_Handler to save to the database
        return feedbackHandler.insertFeedback(patientId, patientName, doctorName, hospitalName, experienceRating, recommendations, feedbackComments);
    }
}
