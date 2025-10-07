package za.ac.cput.factory;

import za.ac.cput.domain.Notification;

import java.time.LocalDateTime;

public class NotificationFactory {
    public static Notification createNotification(String recipientEmail, String message) {
        if (recipientEmail == null || recipientEmail.isBlank() || message == null || message.isBlank()) {
            return null;
        }

        return new Notification.Builder()
                .setRecipientEmail(recipientEmail.trim())
                .setMessage(message.trim())
                .setDateSent(LocalDateTime.now())
                .setStatus("PENDING")
                .build();
    }
}

