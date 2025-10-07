package za.ac.cput.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.service.IApplicationService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * FID WIL MANAGEMENT SYSTEM
 * ApplicationControllerTest.java
 * Author: Kelly Ngoveni
 * Date: 07 October 2025
 */

class ApplicationControllerTest {

    @Mock
    private IApplicationService applicationService;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ApplicationController controller;

    private Application application;
    private Student student;
    private InternshipPost post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        post = new InternshipPost.Builder().setPostId(1).build();
        application = new Application();
    }

    @Test
    void createApplication() {
        when(applicationService.createApplication(application)).thenReturn(application);

        ResponseEntity<?> response = controller.createApplication(application);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(application, response.getBody());
        verify(applicationService, times(1)).createApplication(application);
    }

    @Test
    void getAllApplications() {
        ArrayList<Application> list = new ArrayList<>();
        list.add(application);

        when(applicationService.getAllApplications()).thenReturn(list);

        ResponseEntity<ArrayList<Application>> response = controller.getAllApplications();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getApplicationById() {
        when(applicationService.getApplicationById(1)).thenReturn(application);

        ResponseEntity<?> response = controller.getApplicationById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(application, response.getBody());
    }

    @Test
    void getApplicationsByStudent() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        ArrayList<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationService.getApplicationsByStudent(student)).thenReturn(applications);

        ResponseEntity<?> response = controller.getApplicationsByStudent(studentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, ((ArrayList<?>) response.getBody()).size());
    }

    @Test
    void getApplicationsByPost() {
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationService.getApplicationsByPost(any(InternshipPost.class))).thenReturn(applications);

        ResponseEntity<ArrayList<Application>> response = controller.getApplicationsByPost(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getApplicationsByStatus() {
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(application);

        when(applicationService.getApplicationsByStatus("Pending")).thenReturn(applications);

        ResponseEntity<ArrayList<Application>> response = controller.getApplicationsByStatus("Pending");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateApplicationStatus() {
        when(applicationService.updateApplicationStatus(1, "Approved")).thenReturn(application);

        ResponseEntity<?> response = controller.updateApplicationStatus(1, "Approved");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(application, response.getBody());
    }

    @Test
    void deleteApplication() {
        doNothing().when(applicationService).deleteApplication(1);

        ResponseEntity<String> response = controller.deleteApplication(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Application deleted successfully", response.getBody());
        verify(applicationService, times(1)).deleteApplication(1);
    }
}
