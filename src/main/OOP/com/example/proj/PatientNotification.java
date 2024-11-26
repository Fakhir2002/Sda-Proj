package com.example.proj;

import java.util.List;

class PatientNotification extends Notification {

    private int patientId;

    public PatientNotification(int notificationId, int patientId, String description, boolean isRead) {
        super(notificationId, description, isRead);
        this.patientId = patientId;
    }

    @Override
    public List<Notification> getNotifications(int id) {
        return notificationHandler.getNotificationsByPatient(id);
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

}