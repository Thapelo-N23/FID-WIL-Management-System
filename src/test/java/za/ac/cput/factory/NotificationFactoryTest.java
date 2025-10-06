package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Notification;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void createNotification() {
        String email = "test@example.com";
        String message = "This is a test notification.";

        Notification notification = NotificationFactory.createNotification(email, message);

        assertNotNull(notification, "Notification should not be null");
        assertEquals(email, notification.getRecipientEmail(), "Email should match");
        assertEquals(message, notification.getMessage(), "Message should match");
        assertEquals("PENDING", notification.getStatus(), "Status should be PENDING");
        assertNotNull(notification.getDateSent(), "Date sent should not be null");
        assertTrue(notification.getDateSent().isBefore(LocalDateTime.now().plusSeconds(1)),
                "Date sent should be around now");
    }

    @Test
    void createNotification_withNullEmail_shouldReturnNull() {
        Notification notification = NotificationFactory.createNotification(null, "Message");
        assertNull(notification, "Notification should be null if email is null");
    }

    @Test
    void createNotification_withBlankMessage_shouldReturnNull() {
        Notification notification = NotificationFactory.createNotification("test@example.com", "   ");
        assertNull(notification, "Notification should be null if message is blank");
    }
}
