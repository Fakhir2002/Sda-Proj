package OOP;

import java.util.List;

public class DoctorNotification extends Notification {

    private int doctorId;

    public DoctorNotification(int notificationId, int doctorId, String description, boolean isRead) {
        super(notificationId, description, isRead);
        this.doctorId = doctorId;
    }

    @Override
    public List<Notification> getNotifications(int id) {
        return notificationHandler.getNotificationsByDoctorId(id);
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}