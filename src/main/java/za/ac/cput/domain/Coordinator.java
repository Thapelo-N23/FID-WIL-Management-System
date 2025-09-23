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

    public Coordinator() {}

    public Coordinator(User user, String fullName, String contactNumber, String courseAssigned) {
        this.user = user;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.courseAssigned = courseAssigned;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getCourseAssigned() { return courseAssigned; }
    public void setCourseAssigned(String courseAssigned) { this.courseAssigned = courseAssigned; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
