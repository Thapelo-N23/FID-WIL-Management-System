/**
 * InternshipTrackingSystem
 * InternshipPostController.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.service.InternshipPostService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Internship Posts.
 * Handles all endpoints related to internship postings.
 */
@RestController
@RequestMapping("/api/internshipPost")
@CrossOrigin(origins = "*")
public class InternshipPostController {

    private final InternshipPostService service;

    @Autowired
    public InternshipPostController(InternshipPostService service) {
        this.service = service;
    }

    // -------------------------------
    // 🔹 CREATE Internship Post
    // -------------------------------
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody InternshipPost postRequest) {
        try {
            if (postRequest.getBusiness() == null) {
                return ResponseEntity.badRequest().body("Business information is required.");
            }

            InternshipPost newPost = service.create(
                    postRequest.getTitle(),
                    postRequest.getDescription(),
                    postRequest.getDeadline(),
                    postRequest.getStatus(),
                    postRequest.getBusiness()
            );
            return ResponseEntity.ok(newPost);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating InternshipPost: " + e.getMessage());
        }
    }

    // -------------------------------
    // 🔹 READ by ID
    // -------------------------------
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readPost(@PathVariable int id) {
        Optional<InternshipPost> post = service.read(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // -------------------------------
    // 🔹 GET all Internship Posts
    // -------------------------------
    @GetMapping("/all")
    public ResponseEntity<List<InternshipPost>> getAllPosts() {
        return ResponseEntity.ok(service.getAll());
    }

    // -------------------------------
    // 🔹 GET all Active (Open) Posts
    // -------------------------------
    @GetMapping("/active")
    public ResponseEntity<List<InternshipPost>> getActivePosts() {
        return ResponseEntity.ok(service.getActivePosts());
    }

    // -------------------------------
    // 🔹 GET all Posts by Business
    // -------------------------------
    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<InternshipPost>> getPostsByBusiness(@PathVariable int businessId) {
        return ResponseEntity.ok(service.getPostsByBusiness(businessId));
    }

    // -------------------------------
    // 🔹 UPDATE Post
    // -------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable int id,
            @RequestBody InternshipPost updateRequest
    ) {
        try {
            InternshipPost updated = service.updatePost(
                    id,
                    updateRequest.getTitle(),
                    updateRequest.getDescription(),
                    updateRequest.getDeadline(),
                    updateRequest.getStatus()
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating post: " + e.getMessage());
        }
    }

    // -------------------------------
    // 🔹 CLOSE Internship Post
    // -------------------------------
    @PutMapping("/close/{id}")
    public ResponseEntity<?> closePost(@PathVariable int id) {
        boolean closed = service.closePost(id);
        if (closed) {
            return ResponseEntity.ok("Post closed successfully.");
        } else {
            return ResponseEntity.status(404).body("Post not found or already closed.");
        }
    }

    // -------------------------------
    // 🔹 DELETE by ID
    // -------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok("InternshipPost deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }

    // -------------------------------
    // 🔹 DELETE by Business ID
    // -------------------------------
    @DeleteMapping("/delete/business/{businessId}")
    public ResponseEntity<?> deletePostsByBusiness(@PathVariable int businessId) {
        service.deleteByBusiness(businessId);
        return ResponseEntity.ok("All posts for Business ID " + businessId + " deleted successfully.");
    }
}

