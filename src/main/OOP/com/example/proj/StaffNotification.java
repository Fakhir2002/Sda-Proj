package com.example.proj;

import java.util.List;

class StaffNotification extends Notification {

    private int staffId;

    public StaffNotification(int notificationId, int staffId, String description, boolean isRead) {
        super(notificationId, description, isRead);
        this.staffId = staffId;
    }

    @Override
    public List<Notification> getNotifications(int id) {
        return notificationHandler.getNotificationsByStaffId(id);
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}