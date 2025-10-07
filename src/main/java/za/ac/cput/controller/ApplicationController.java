package za.ac.cput.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.service.IApplicationService;

import java.util.ArrayList;
import java.util.Optional;

/**
 * FID WIL MANAGEMENT SYSTEM
 * ApplicationController.java
 * Author: Kelly Ngoveni
 * Date: 02 October 2025
 */

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final IApplicationService applicationService;
    private final StudentRepository studentRepository;

    public ApplicationController(IApplicationService applicationService, StudentRepository studentRepository) {
        this.applicationService = applicationService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createApplication(@RequestBody Application application) {
        try {
            Application created = applicationService.createApplication(application);
            if (created == null) {
                return ResponseEntity.badRequest().body("Invalid application input");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating application: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable int id) {
        Application application = applicationService.getApplicationById(id);
        if (application != null) {
            return ResponseEntity.ok(application);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getApplicationsByStudent(@PathVariable Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + studentId + " not found");
        }

        ArrayList<Application> applications = applicationService.getApplicationsByStudent(studentOpt.get());
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ArrayList<Application>> getApplicationsByPost(@PathVariable int postId) {
        InternshipPost post = new InternshipPost.Builder().setPostId(postId).build();
        ArrayList<Application> applications = applicationService.getApplicationsByPost(post);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ArrayList<Application>> getApplicationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable int id, @RequestParam String newStatus) {
        try {
            Application updated = applicationService.updateApplicationStatus(id, newStatus);
            if (updated == null) {
                return ResponseEntity.badRequest().body("Invalid application or status");
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating application status: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable int id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Application deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting application: " + e.getMessage());
        }
    }
}
