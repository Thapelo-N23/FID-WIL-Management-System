package za.ac.cput.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Notification;
import za.ac.cput.service.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Notification request) {
        try {
            Notification notification = notificationService.createNotification(
                    request.getRecipientEmail(),
                    request.getMessage()
            );
            if (notification == null) {
                return ResponseEntity.badRequest().body("Invalid notification input");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating notification: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/send")
    public ResponseEntity<?> sendNotification(@PathVariable Long id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            if (notification == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
            }
            Notification sent = notificationService.sendNotification(notification);
            return ResponseEntity.ok(sent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending notification: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<Notification>> getNotificationsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(notificationService.getNotificationsByEmail(email));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Notification>> getPendingNotifications() {
        return ResponseEntity.ok(notificationService.getPendingNotifications());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok("Notification deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting notification: " + e.getMessage());
        }
    }
}
