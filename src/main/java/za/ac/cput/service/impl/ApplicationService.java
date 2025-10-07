package za.ac.cput.service.impl;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.repository.ApplicationRepository;
import za.ac.cput.service.IApplicationService;

import java.util.ArrayList;
import java.util.Optional;

/**
 * FID WIL MANAGEMENT SYSTEM
 * ApplicationService.java
 * Author: Kelly Ngoveni
 * Date: 02 October 2025
 */

@Service
public class ApplicationService implements IApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application createApplication(Application application) {
        if (application == null ||
                application.getStudent() == null ||
                application.getPost() == null ||
                application.getStatus() == null ||
                application.getStatus().trim().isEmpty()) {
            System.out.println("Invalid application input — creation failed");
            return null;
        }
        return applicationRepository.save(application);
    }

    @Override
    public Application getApplicationById(int id) {
        Optional<Application> app = applicationRepository.findById(id);
        return app.orElse(null);
    }

    @Override
    public ArrayList<Application> getAllApplications() {
        return new ArrayList<>(applicationRepository.findAll());
    }

    @Override
    public ArrayList<Application> getApplicationsByStudent(Student student) {
        return new ArrayList<>(applicationRepository.findByStudent(student));
    }

    @Override
    public ArrayList<Application> getApplicationsByPost(InternshipPost post) {
        return new ArrayList<>(applicationRepository.findByPost(post));
    }

    @Override
    public ArrayList<Application> getApplicationsByStatus(String status) {
        return new ArrayList<>(applicationRepository.findByStatus(status));
    }

    @Override
    public Application updateApplicationStatus(int id, String newStatus) {
        Application existing = getApplicationById(id);
        if (existing == null) {
            System.out.println("Application not found — cannot update status");
            return null;
        }

        boolean updated = existing.updateStatus(newStatus);
        if (!updated) {
            System.out.println("Invalid status provided — update aborted");
            return null;
        }

        return applicationRepository.save(existing);
    }

    @Override
    public void deleteApplication(int id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            System.out.println("Application deleted successfully: ID " + id);
        } else {
            System.out.println("Application not found — delete skipped");
        }
    }
}
