package com.example.proj;

import com.example.temp.DB_HANDLER.Notification_Handler;

import java.util.List;

public class Notification {

    private int notificationId = 0;
    private int patientId;
    private int doctorId;
    private String description;
    private boolean isRead;
    private static Notification_Handler notificationHandler = new Notification_Handler();

    // Constructor to create a new Notification object
    public Notification(int notificationId, int patientId, int doctorId, String description, boolean isRead) {
        this.notificationId = notificationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.description = description;
        this.isRead = isRead;
    }

    // Constructor to create a new Notification without NotificationID (for saving new notifications)
    public Notification(int patientId, int doctorId, String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.description = description;
        this.isRead = false; // Default value for new notifications
    }

    // Method to get notifications by patient ID
    public static List<Notification> getNotificationsByPatientId(int id) {
        return notificationHandler.getNotificationsByPatient(id);
    }

    // Method to mark a notification as read
    public static void markAsRead(int notificationId) {
        boolean success = notificationHandler.markAsRead(notificationId);
        if (success) {
            System.out.println("Notification ID " + notificationId + " marked as read.");
        } else {
            System.err.println("Failed to mark Notification ID " + notificationId + " as read.");
        }
    }

    // Getter and Setter for NotificationID
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    // Getter and Setter for PatientID
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // Getter and Setter for DoctorID
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    // Getter and Setter for Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for isRead
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Override toString() method to display the notification details
    @Override
    public String toString() {
        return "NotificationID: " + notificationId + ", PatientID: " + patientId +
                ", DoctorID: " + doctorId + ", Description: " + description +
                ", IsRead: " + isRead;
    }

    // Save the notification to the database
    public boolean saveNotification() {
        return notificationHandler.saveNotification(this.patientId, this.doctorId, this.description);
    }

    // Get a notification by its ID
    public static Notification getNotificationById(int notificationId) {
        return notificationHandler.getNotificationById(notificationId);
    }

    // Delete a notification by its ID
    public static boolean deleteNotification(int notificationId) {
        return notificationHandler.deleteNotification(notificationId);
    }

    // Update the description of the notification
    public boolean updateNotificationDescription(String newDescription) {
        this.description = newDescription;
        return notificationHandler.updateNotificationDescription(this.notificationId, newDescription);
    }
}
