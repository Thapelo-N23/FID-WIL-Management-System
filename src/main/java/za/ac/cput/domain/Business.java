package za.ac.cput.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.Set;

@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "description", length = 500)
    private String description;

// TODO: Uncomment when InternshipPost is implemented
// @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
// private Set<InternshipPost> posts = new HashSet<>();

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public Business() {}

    private Business(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.companyName = builder.companyName;
        this.industry = builder.industry;
        this.location = builder.location;
        this.description = builder.description;
        // this.posts = builder.posts;
        this.active = builder.active;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getCompanyName() { return companyName; }
    public String getIndustry() { return industry; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    // public Set<InternshipPost> getPosts() { return posts; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setUser(User user) { this.user = user; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setActive(Boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", location='" + location + '\'' +
                ", active=" + active +
                '}';
    }

    // Builder
    public static class Builder {
        private Long id;
        private User user;
        private String companyName;
        private String industry;
        private String location;
        private String description;
        // private Set<InternshipPost> posts = new HashSet<>();
        private Boolean active = true;
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

        public Builder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder setIndustry(String industry) {
            this.industry = industry;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        // public Builder setPosts(Set<InternshipPost> posts) {
        //     this.posts = posts;
        //     return this;
        // }

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
            this.user = business.user;
            this.companyName = business.companyName;
            this.industry = business.industry;
            this.location = business.location;
            this.description = business.description;
            // this.posts = business.posts;
            this.active = business.active;
            this.createdAt = business.createdAt;
            this.updatedAt = business.updatedAt;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }


}