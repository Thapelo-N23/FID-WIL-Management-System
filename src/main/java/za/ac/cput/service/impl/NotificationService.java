package za.ac.cput.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Notification;
import za.ac.cput.factory.NotificationFactory;
import za.ac.cput.repository.NotificationRepository;
import za.ac.cput.service.INotificationService;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository repository;

    @Autowired
    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification createNotification(String recipientEmail, String message) {
        Notification notification = NotificationFactory.createNotification(recipientEmail, message);
        if (notification != null) {
            return repository.save(notification);
        }
        return null; // invalid input
    }

    @Override
    public Notification sendNotification(Notification notification) {
        if (notification == null) {
            return null;
        }

        try {
            // Use the domain method to "send"
            notification.sendNotification();
            return repository.save(notification);
        } catch (Exception e) {
            notification.setStatus("FAILED");
            return repository.save(notification);
        }
    }

    @Override
    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = repository.findById(id);
        return notification.orElse(null);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @Override
    public List<Notification> getNotificationsByEmail(String email) {
        return repository.findByRecipientEmail(email);
    }

    @Override
    public List<Notification> getPendingNotifications() {
        return repository.findByStatus("PENDING");
    }

    @Override
    public void deleteNotification(Long id) {
        repository.deleteById(id);
    }
}
