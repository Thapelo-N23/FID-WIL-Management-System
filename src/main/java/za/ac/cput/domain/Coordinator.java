package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "coordinators")
public class Coordinator {

    @Id
    @Column(name = "coordinator_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "coordinator_id")
    private User user;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(name = "course_assigned", length = 100)
    private String courseAssigned;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Default constructor
    public Coordinator() {}

    // Constructors
    public Coordinator(User user, String fullName, String contactNumber, String courseAssigned) {
        this.user = user;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.courseAssigned = courseAssigned;
    }

    private Coordinator(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.fullName = builder.fullName;
        this.contactNumber = builder.contactNumber;
        this.courseAssigned = builder.courseAssigned;
        this.createdAt = builder.createdAt;
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }
    public String getContactNumber() { return contactNumber; }
    public String getCourseAssigned() { return courseAssigned; }
    public LocalDateTime getCreatedAt() { return createdAt; }

//    // Setters
//    public void setUser(User user) { this.user = user; }
//    public void setFullName(String fullName) { this.fullName = fullName; }
//    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
//    public void setCourseAssigned(String courseAssigned) { this.courseAssigned = courseAssigned; }

    @Override
    public String toString() {
        return "Coordinator{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", courseAssigned='" + courseAssigned + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    // Builder Class
    public static class Builder {
        private Long id;
        private User user;
        private String fullName;
        private String contactNumber;
        private String courseAssigned;
        private LocalDateTime createdAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public Builder setCourseAssigned(String courseAssigned) {
            this.courseAssigned = courseAssigned;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(Coordinator coordinator) {
            this.id = coordinator.id;
            this.user = coordinator.user;
            this.fullName = coordinator.fullName;
            this.contactNumber = coordinator.contactNumber;
            this.courseAssigned = coordinator.courseAssigned;
            this.createdAt = coordinator.createdAt;
            return this;
        }

        public Coordinator build() {
            return new Coordinator(this);
        }
    }
}