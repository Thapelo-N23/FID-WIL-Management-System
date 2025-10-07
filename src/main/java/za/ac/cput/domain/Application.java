package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int applicationID;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private InternshipPost post; // ✅ fixed type name here

    @Column(nullable = false, length = 50)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Application() {}

    private Application(Builder builder) {
        this.applicationID = builder.applicationID;
        this.student = builder.student;
        this.post = builder.post;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // ✅ validation logic for updating status
    public boolean updateStatus(String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return false;
        }
        this.status = newStatus;
        return true;
    }

    // Getters
    public int getApplicationID() { return applicationID; }
    public Student getStudent() { return student; }
    public InternshipPost getPost() { return post; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setStudent(Student student) { this.student = student; }
    public void setPost(InternshipPost post) { this.post = post; }
    public void setStatus(String status) { this.status = status; }

    // Builder Pattern
    public static class Builder {
        private int applicationID;
        private Student student;
        private InternshipPost post;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setApplicationID(int applicationID) {
            this.applicationID = applicationID;
            return this;
        }

        public Builder setStudent(Student student) {
            this.student = student;
            return this;
        }

        public Builder setPost(InternshipPost post) {
            this.post = post;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder copy(Application application) {
            this.applicationID = application.applicationID;
            this.student = application.student;
            this.post = application.post;
            this.status = application.status;
            this.createdAt = application.createdAt;
            this.updatedAt = application.updatedAt;
            return this;
        }

        public Application build() {
            return new Application(this);
        }
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", student=" + (student != null ? student.toString() : "null") +
                ", post=" + (post != null ? post.toString() : "null") +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
