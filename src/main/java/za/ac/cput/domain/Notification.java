package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "recipient_email", nullable = false, length = 100)
    private String recipientEmail;

    @Column(nullable = false, length = 500)
    private String message;

    @CreationTimestamp
    @Column(name = "date_sent", updatable = false)
    private LocalDateTime dateSent;

    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    public Notification() {}

    private Notification(Builder builder) {
        this.id = builder.id;
        this.recipientEmail = builder.recipientEmail;
        this.message = builder.message;
        this.dateSent = builder.dateSent;
        this.status = builder.status;
    }

    // Business Logic
    public void sendNotification() {
        try {
            // Imagine sending email/SMS logic here
            System.out.println("Sending notification to " + recipientEmail + ": " + message);

            this.status = "SENT";
        } catch (Exception e) {
            this.status = "FAILED";
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getRecipientEmail() { return recipientEmail; }
    public String getMessage() { return message; }
    public LocalDateTime getDateSent() { return dateSent; }
    public String getStatus() { return status; }

    // Setters
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", dateSent=" + dateSent +
                '}';
    }

    // Builder Class
    public static class Builder {
        private Long id;
        private String recipientEmail;
        private String message;
        private LocalDateTime dateSent;
        private String status = "PENDING";

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setRecipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDateSent(LocalDateTime dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Notification notification) {
            this.id = notification.id;
            this.recipientEmail = notification.recipientEmail;
            this.message = notification.message;
            this.dateSent = notification.dateSent;
            this.status = notification.status;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
