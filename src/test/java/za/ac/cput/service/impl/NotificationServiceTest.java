package za.ac.cput.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import za.ac.cput.domain.Notification;
import za.ac.cput.repository.NotificationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService service;

    private Notification notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notification = new Notification.Builder()
                .setId(1L)
                .setRecipientEmail("user@example.com")
                .setMessage("Test message")
                .setStatus("PENDING")
                .build();
    }

    @Test
    void createNotification_success() {
        when(repository.save(any(Notification.class))).thenReturn(notification);

        Notification result = service.createNotification("user@example.com", "Test message");

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        verify(repository, times(1)).save(any(Notification.class));
    }

    @Test
    void createNotification_invalidInput() {
        Notification result = service.createNotification("", ""); // invalid input

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void sendNotification_success() {
        when(repository.save(any(Notification.class))).thenReturn(notification);

        Notification result = service.sendNotification(notification);

        assertNotNull(result);
        assertEquals("SENT", result.getStatus());
        verify(repository).save(notification);
    }

    @Test
    void sendNotification_nullInput() {
        Notification result = service.sendNotification(null);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void getNotificationById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(notification));

        Notification result = service.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(notification, result);
    }

    @Test
    void getNotificationById_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Notification result = service.getNotificationById(1L);

        assertNull(result);
    }

    @Test
    void getAllNotifications() {
        List<Notification> list = Arrays.asList(notification);
        when(repository.findAll()).thenReturn(list);

        List<Notification> result = service.getAllNotifications();

        assertEquals(1, result.size());
        assertEquals(notification, result.get(0));
    }

    @Test
    void getNotificationsByEmail() {
        List<Notification> list = Arrays.asList(notification);
        when(repository.findByRecipientEmail("user@example.com")).thenReturn(list);

        List<Notification> result = service.getNotificationsByEmail("user@example.com");

        assertEquals(1, result.size());
        assertEquals("user@example.com", result.get(0).getRecipientEmail());
    }

    @Test
    void getPendingNotifications() {
        List<Notification> list = Arrays.asList(notification);
        when(repository.findByStatus("PENDING")).thenReturn(list);

        List<Notification> result = service.getPendingNotifications();

        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }

    @Test
    void deleteNotification() {
        doNothing().when(repository).deleteById(1L);

        service.deleteNotification(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
