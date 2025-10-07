/**
 * InternshipTrackingSystem
 * InternshipPostService.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.factory.InternshipPostFactory;
import za.ac.cput.repository.InternshipPostRepository;
import za.ac.cput.util.Helper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipPostService {

    private final InternshipPostRepository repository;

    @Autowired
    public InternshipPostService(InternshipPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Create and persist a new InternshipPost.
     */
    public InternshipPost create(String title, String description, Date deadline, String status, Business business) {
        InternshipPost post = InternshipPostFactory.createInternshipPost(title, description, deadline, status, business);
        if (post == null) {
            throw new IllegalArgumentException("Failed to create InternshipPost: invalid data provided.");
        }
        return repository.save(post);
    }

    /**
     * Retrieve a post by its ID.
     */
    public Optional<InternshipPost> read(int postId) {
        return repository.findById(postId);
    }

    /**
     * Get all internship posts.
     */
    public List<InternshipPost> getAll() {
        return repository.findAll();
    }

    /**
     * Get all posts with a specific status (e.g., "Open", "Closed").
     */
    public List<InternshipPost> getByStatus(String status) {
        if (Helper.isNullOrEmpty(status)) return List.of();
        return repository.findByStatus(status.trim());
    }

    /**
     * Get all active posts (deadline still valid).
     */
    public List<InternshipPost> getActivePosts() {
        return repository.findByDeadlineAfter(new Date());
    }

    /**
     * Get all posts belonging to a specific business.
     */
    public List<InternshipPost> getPostsByBusiness(int businessId) {
        return repository.findByBusiness_BusinessId(businessId);
    }

    /**
     * Update post details.
     */
    public InternshipPost updatePost(int postId, String newTitle, String newDescription, Date newDeadline, String newStatus) {
        Optional<InternshipPost> existingPostOpt = repository.findById(postId);
        if (existingPostOpt.isEmpty()) {
            throw new IllegalArgumentException("InternshipPost not found with ID: " + postId);
        }

        InternshipPost existingPost = existingPostOpt.get();

        InternshipPost updatedPost = new InternshipPost.Builder()
                .copy(existingPost)
                .setTitle(Helper.isNullOrEmpty(newTitle) ? existingPost.getTitle() : newTitle)
                .setDescription(Helper.isNullOrEmpty(newDescription) ? existingPost.getDescription() : newDescription)
                .setDeadline(newDeadline != null ? newDeadline : existingPost.getDeadline())
                .setStatus(Helper.isNullOrEmpty(newStatus) ? existingPost.getStatus() : newStatus)
                .build();

        return repository.save(updatedPost);
    }

    /**
     * Close an internship post (status = "Closed").
     */
    public boolean closePost(int postId) {
        Optional<InternshipPost> postOpt = repository.findById(postId);
        if (postOpt.isEmpty()) return false;

        InternshipPost post = postOpt.get();
        post = new InternshipPost.Builder()
                .copy(post)
                .setStatus("Closed")
                .build();

        repository.save(post);
        return true;
    }

    /**
     * Delete an internship post by ID.
     */
    public boolean delete(int postId) {
        if (!repository.existsById(postId)) return false;
        repository.deleteById(postId);
        return true;
    }

    /**
     * Delete all posts belonging to a business (e.g. when a company account is removed).
     */
    public void deleteByBusiness(int businessId) {
        repository.deleteByBusiness_BusinessId(businessId);
    }
}

