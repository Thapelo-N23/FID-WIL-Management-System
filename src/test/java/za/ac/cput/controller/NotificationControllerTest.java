package za.ac.cput.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Notification;
import za.ac.cput.service.INotificationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private Notification notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Use Builder to create notification
        notification = new Notification.Builder()
                .setId(1L)
                .setRecipientEmail("user@example.com")
                .setMessage("Test message")
                .setStatus("PENDING")
                .build();
    }

    @Test
    void createNotification_success() {
        when(notificationService.createNotification("user@example.com", "Test message"))
                .thenReturn(notification);

        ResponseEntity<?> response = notificationController.createNotification(notification);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService, times(1))
                .createNotification("user@example.com", "Test message");
    }

    @Test
    void createNotification_invalidInput() {
        when(notificationService.createNotification(anyString(), anyString())).thenReturn(null);

        ResponseEntity<?> response = notificationController.createNotification(notification);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid notification input", response.getBody());
        verify(notificationService, times(1))
                .createNotification("user@example.com", "Test message");
    }

    @Test
    void sendNotification_success() {
        when(notificationService.getNotificationById(1L)).thenReturn(notification);
        when(notificationService.sendNotification(notification)).thenAnswer(invocation -> {
            notification.sendNotification(); // simulate sending
            return notification;
        });

        ResponseEntity<?> response = notificationController.sendNotification(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SENT", ((Notification) response.getBody()).getStatus());
        verify(notificationService).sendNotification(notification);
    }

    @Test
    void sendNotification_notFound() {
        when(notificationService.getNotificationById(1L)).thenReturn(null);

        ResponseEntity<?> response = notificationController.sendNotification(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Notification not found", response.getBody());
    }

    @Test
    void getAllNotifications() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationService.getAllNotifications()).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getAllNotifications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
    }

    @Test
    void getNotificationById_found() {
        when(notificationService.getNotificationById(1L)).thenReturn(notification);

        ResponseEntity<?> response = notificationController.getNotificationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());
    }

    @Test
    void getNotificationById_notFound() {
        when(notificationService.getNotificationById(1L)).thenReturn(null);

        ResponseEntity<?> response = notificationController.getNotificationById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Notification not found", response.getBody());
    }

    @Test
    void getNotificationsByEmail() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationService.getNotificationsByEmail("user@example.com")).thenReturn(notifications);

        ResponseEntity<List<Notification>> response =
                notificationController.getNotificationsByEmail("user@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
    }

    @Test
    void getPendingNotifications() {
        List<Notification> pending = Arrays.asList(notification);
        when(notificationService.getPendingNotifications()).thenReturn(pending);

        ResponseEntity<List<Notification>> response = notificationController.getPendingNotifications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pending, response.getBody());
    }

    @Test
    void deleteNotification_success() {
        doNothing().when(notificationService).deleteNotification(1L);

        ResponseEntity<String> response = notificationController.deleteNotification(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification deleted successfully", response.getBody());
        verify(notificationService).deleteNotification(1L);
    }

    @Test
    void deleteNotification_failure() {
        doThrow(new RuntimeException("Delete failed")).when(notificationService).deleteNotification(1L);

        ResponseEntity<String> response = notificationController.deleteNotification(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error deleting notification"));
        verify(notificationService).deleteNotification(1L);
    }
}
