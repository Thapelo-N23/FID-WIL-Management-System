package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "performed_by", nullable = false, length = 100)
    private String performedBy;

    @Column(name = "target_user", length = 100)
    private String targetUser;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "performed_at", updatable = false)
    private LocalDateTime performedAt;

    // Default constructor
    public ActivityLog() {}

    // Builder constructor
    private ActivityLog(Builder builder) {
        this.id = builder.id;
        this.action = builder.action;
        this.description = builder.description;
        this.performedBy = builder.performedBy;
        this.targetUser = builder.targetUser;
        this.ipAddress = builder.ipAddress;
        this.performedAt = builder.performedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getAction() { return action; }
    public String getDescription() { return description; }
    public String getPerformedBy() { return performedBy; }
    public String getTargetUser() { return targetUser; }
    public String getIpAddress() { return ipAddress; }
    public LocalDateTime getPerformedAt() { return performedAt; }

    @Override
    public String toString() {
        return "ActivityLog{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", targetUser='" + targetUser + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", performedAt=" + performedAt +
                '}';
    }

    // Builder Class
    public static class Builder {
        private Long id;
        private String action;
        private String description;
        private String performedBy;
        private String targetUser;
        private String ipAddress;
        private LocalDateTime performedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPerformedBy(String performedBy) {
            this.performedBy = performedBy;
            return this;
        }

        public Builder setTargetUser(String targetUser) {
            this.targetUser = targetUser;
            return this;
        }

        public Builder setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder setPerformedAt(LocalDateTime performedAt) {
            this.performedAt = performedAt;
            return this;
        }

        public Builder copy(ActivityLog activityLog) {
            this.id = activityLog.id;
            this.action = activityLog.action;
            this.description = activityLog.description;
            this.performedBy = activityLog.performedBy;
            this.targetUser = activityLog.targetUser;
            this.ipAddress = activityLog.ipAddress;
            this.performedAt = activityLog.performedAt;
            return this;
        }

        public ActivityLog build() {
            return new ActivityLog(this);
        }
    }
}