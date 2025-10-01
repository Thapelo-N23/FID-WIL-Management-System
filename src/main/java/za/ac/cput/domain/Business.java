/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "registration_number", nullable = false, unique = true, length = 50)
    private String registrationNumber;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships (commented until other classes are ready)
    /*
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternshipPost> posts = new ArrayList<>();
    */

    // Default constructor
    public Business() {}

    private Business(Builder builder) {
        this.id = builder.id;
        this.companyName = builder.companyName;
        this.registrationNumber = builder.registrationNumber;
        this.industry = builder.industry;
        this.contactNumber = builder.contactNumber;
        this.email = builder.email;
        this.address = builder.address;
        this.active = builder.active;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getIndustry() { return industry; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setActive(Boolean active) { this.active = active; }

    // Builder class
    public static class Builder {
        private Long id;
        private String companyName;
        private String registrationNumber;
        private String industry;
        private String contactNumber;
        private String email;
        private String address;
        private Boolean active = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setIndustry(String industry) {
            this.industry = industry;
            return this;
        }

        public Builder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
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

        public Builder copy(Business business) {
            this.id = business.id;
            this.companyName = business.companyName;
            this.registrationNumber = business.registrationNumber;
            this.industry = business.industry;
            this.contactNumber = business.contactNumber;
            this.email = business.email;
            this.address = business.address;
            this.active = business.active;
            this.createdAt = business.createdAt;
            this.updatedAt = business.updatedAt;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", industry='" + industry + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
