package za.ac.cput.service;

import za.ac.cput.domain.Notification;

import java.util.List;

public interface INotificationService {
    Notification createNotification(String recipientEmail, String message);
    Notification sendNotification(Notification notification);
    Notification getNotificationById(Long id);
    List<Notification> getAllNotifications();
    List<Notification> getNotificationsByEmail(String email);
    List<Notification> getPendingNotifications();
    void deleteNotification(Long id);
}

