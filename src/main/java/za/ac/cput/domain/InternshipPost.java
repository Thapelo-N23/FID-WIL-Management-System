package za.ac.cput.domain;

/**
 * FID WIL MANAGEMENT SYSTEM
 * InternshipPost.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "internship_posts")
public class InternshipPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @JsonProperty("title")
    @Column(name = "title", nullable = false)
    private String title;

    @JsonProperty("description")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @JsonProperty("deadline")
    @Temporal(TemporalType.DATE)
    @Column(name = "deadline")
    private Date deadline;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    // Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonBackReference("business-posts")
    private Business business;



    // Constructors

    public InternshipPost() {}

    private InternshipPost(Builder builder) {
        this.postId = builder.postId;
        this.title = builder.title;
        this.description = builder.description;
        this.deadline = builder.deadline;
        this.status = builder.status;
        this.business = builder.business;
    }

    // Getters

    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public Business getBusiness() {
        return business;
    }



    // toString
    @Override
    public String toString() {
        return "InternshipPost{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                '}';
    }

    // Builder Class
    public static class Builder {
        private int postId;
        private String title;
        private String description;
        private Date deadline;
        private String status;
        private Business business;

        public Builder setPostId(int postId) {
            this.postId = postId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDeadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setBusiness(Business business) {
            this.business = business;
            return this;
        }

//        public Builder setApplications(List<Application> applications) {
//            this.applications = applications;
//            return this;
//        }
//
//        public Builder setApplication(Application application) {
//            if (application != null) {
//                this.applications = Collections.singletonList(application);
//            } else {
//                this.applications = new ArrayList<>();
//            }
//            return this;
//        }

        public Builder copy(InternshipPost post) {
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.deadline = post.getDeadline();
            this.status = post.getStatus();
            this.business = post.getBusiness();
            return this;
        }

        public InternshipPost build() {
            return new InternshipPost(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "postId=" + postId +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", deadline=" + deadline +
                    ", status='" + status + '\'' +
                    ", business=" + business +
                    '}';
        }
    }
}

