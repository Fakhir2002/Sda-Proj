package com.example.proj;

public class NotificationFactory {
    public static Notification createNotification(int notificationId, String description, boolean isRead,
                                                  Integer patientId, Integer doctorId, Integer staffId) {
        if (patientId != null) {
            return new PatientNotification(notificationId, patientId, description, isRead);
        } else if (doctorId != null) {
            return new DoctorNotification(notificationId, doctorId, description, isRead);
        } else if (staffId != null) {
            return new StaffNotification(notificationId, staffId, description, isRead);
        } else {
            throw new IllegalArgumentException("Invalid notification type: No valid ID provided.");
        }
    }
}
