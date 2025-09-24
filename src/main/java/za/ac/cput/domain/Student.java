package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "student_number", unique = true, length = 20)
    private String studentNumber;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(length = 100)
    private String course;

    @Column(name = "cv_file_path")
    private String cvFilePath;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(length = 20)
    private String status = "UNPLACED"; // PLACED, UNPLACED, PENDING

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Student() {}

    private Student(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.studentNumber = builder.studentNumber;
        this.fullName = builder.fullName;
        this.contactNumber = builder.contactNumber;
        this.course = builder.course;
        this.cvFilePath = builder.cvFilePath;
        this.active = builder.active;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getStudentNumber() { return studentNumber; }
    public String getFullName() { return fullName; }
    public String getContactNumber() { return contactNumber; }
    public String getCourse() { return course; }
    public String getCvFilePath() { return cvFilePath; }
    public Boolean getActive() { return active; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setUser(User user) { this.user = user; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setCourse(String course) { this.course = course; }
    public void setCvFilePath(String cvFilePath) { this.cvFilePath = cvFilePath; }
    public void setActive(Boolean active) { this.active = active; }
    public void setStatus(String status) { this.status = status; }

    // Builder Class
    public static class Builder {
        private Long id;
        private User user;
        private String studentNumber;
        private String fullName;
        private String contactNumber;
        private String course;
        private String cvFilePath;
        private Boolean active = true;
        private String status = "UNPLACED";
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
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

        public Builder setCourse(String course) {
            this.course = course;
            return this;
        }

        public Builder setCvFilePath(String cvFilePath) {
            this.cvFilePath = cvFilePath;
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
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

        public Builder copy(Student student) {
            this.id = student.id;
            this.user = student.user;
            this.studentNumber = student.studentNumber;
            this.fullName = student.fullName;
            this.contactNumber = student.contactNumber;
            this.course = student.course;
            this.cvFilePath = student.cvFilePath;
            this.active = student.active;
            this.status = student.status;
            this.createdAt = student.createdAt;
            this.updatedAt = student.updatedAt;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", course='" + course + '\'' +
                ", active=" + active +
                ", status='" + status + '\'' +
                '}';
    }
}