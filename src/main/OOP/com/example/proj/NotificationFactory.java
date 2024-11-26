package com.example.proj;

public class NotificationFactory {
    public static Notification createNotification(int notificationId, String description, boolean isRead,
                                                  Integer patientId, Integer doctorId, Integer staffId) {
        // Ensure that 0 is treated as an invalid ID
        if (patientId != null && patientId > 0) {
            return new PatientNotification(notificationId, patientId, description, isRead);
        } else if (doctorId != null && doctorId > 0) {
            return new DoctorNotification(notificationId, doctorId, description, isRead);
        } else if (staffId != null && staffId > 0) {
            return new StaffNotification(notificationId, staffId, description, isRead);
        } else {
            throw new IllegalArgumentException("Invalid notification type: No valid ID provided.");
        }
    }
}
