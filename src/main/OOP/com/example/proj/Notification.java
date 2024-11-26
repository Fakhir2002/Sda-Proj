package com.example.proj;

import Database.Notification_Handler;

import java.util.List;

// Abstract base class
public abstract class Notification {

    protected int notificationId = 0;
    protected String description;
    protected boolean isRead;
    protected static Notification_Handler notificationHandler = new Notification_Handler();

    // Constructor for creating notifications
    public Notification(int notificationId, String description, boolean isRead) {
        this.notificationId = notificationId;
        this.description = description;
        this.isRead = isRead;
    }

    // Abstract method for retrieving notifications
    public abstract List<Notification> getNotifications(int id);

    // Method to mark a notification as read
    public void markAsRead() {
        boolean success = notificationHandler.markAsRead(notificationId);
        if (success) {
            System.out.println("Notification ID " + notificationId + " marked as read.");
            this.isRead = true;
        } else {
            System.err.println("Failed to mark Notification ID " + notificationId + " as read.");
        }
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "NotificationID: " + notificationId + ", Description: " + description + ", IsRead: " + isRead;
    }

    public boolean saveNotification() {
        if (this instanceof PatientNotification) {
            PatientNotification patientNotification = (PatientNotification) this;
            System.out.println("Saving Patient Notification: " + patientNotification.getDescription());
            return notificationHandler.saveNotification(patientNotification.getPatientId(), null, null, patientNotification.getDescription());
        } else if (this instanceof DoctorNotification) {
            DoctorNotification doctorNotification = (DoctorNotification) this;
            System.out.println("Saving Doctor Notification: " + doctorNotification.getDescription());
            return notificationHandler.saveNotification(null, doctorNotification.getDoctorId(), null, doctorNotification.getDescription());
        } else if (this instanceof StaffNotification) {
            StaffNotification staffNotification = (StaffNotification) this;
            System.out.println("Saving Staff Notification: " + staffNotification.getDescription());
            return notificationHandler.saveNotification(null, null, staffNotification.getStaffId(), staffNotification.getDescription());
        }
        System.out.println("Notification type not matched.");
        return false;
    }

    // Delete a notification from the database
    public boolean delete() {
        return notificationHandler.delete(notificationId);
    }
}
