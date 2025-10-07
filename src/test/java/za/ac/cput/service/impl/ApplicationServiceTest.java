package za.ac.cput.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.repository.ApplicationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    private ApplicationRepository applicationRepository;
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        applicationRepository = mock(ApplicationRepository.class);
        applicationService = new ApplicationService(applicationRepository);
    }

    @Test
    void createApplication_ValidApplication_ShouldSave() {
        Student student = new Student.Builder().setFullName("John Doe").build();
        InternshipPost post = new InternshipPost.Builder().setTitle("Intern").build();
        Application app = new Application.Builder()
                .setStudent(student)
                .setPost(post)
                .setStatus("Pending")
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now())
                .build();

        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.createApplication(app);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        verify(applicationRepository, times(1)).save(app);
    }

    @Test
    void createApplication_InvalidApplication_ShouldReturnNull() {
        Application app = new Application.Builder().build(); // missing fields

        Application result = applicationService.createApplication(app);

        assertNull(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void getApplicationById_Existing_ShouldReturnApplication() {
        Application app = new Application.Builder().setStatus("Pending").build();
        when(applicationRepository.findById(1)).thenReturn(Optional.of(app));

        Application result = applicationService.getApplicationById(1);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
    }

    @Test
    void getApplicationById_NotFound_ShouldReturnNull() {
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        Application result = applicationService.getApplicationById(1);

        assertNull(result);
    }

    @Test
    void getAllApplications_ShouldReturnList() {
        List<Application> list = new ArrayList<>();
        list.add(new Application.Builder().setStatus("Pending").build());
        when(applicationRepository.findAll()).thenReturn(list);

        ArrayList<Application> result = applicationService.getAllApplications();

        assertEquals(1, result.size());
        assertEquals("Pending", result.get(0).getStatus());
    }

    @Test
    void getApplicationsByStudent_ShouldReturnList() {
        Student student = new Student.Builder().setFullName("John").build();
        List<Application> list = List.of(new Application.Builder().setStudent(student).setStatus("Pending").build());
        when(applicationRepository.findByStudent(student)).thenReturn(list);

        ArrayList<Application> result = applicationService.getApplicationsByStudent(student);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0).getStudent());
    }

    @Test
    void getApplicationsByPost_ShouldReturnList() {
        InternshipPost post = new InternshipPost.Builder().setTitle("Intern").build();
        List<Application> list = List.of(new Application.Builder().setPost(post).setStatus("Pending").build());
        when(applicationRepository.findByPost(post)).thenReturn(list);

        ArrayList<Application> result = applicationService.getApplicationsByPost(post);

        assertEquals(1, result.size());
        assertEquals(post, result.get(0).getPost());
    }

    @Test
    void getApplicationsByStatus_ShouldReturnList() {
        String status = "Pending";
        List<Application> list = List.of(new Application.Builder().setStatus(status).build());
        when(applicationRepository.findByStatus(status)).thenReturn(list);

        ArrayList<Application> result = applicationService.getApplicationsByStatus(status);

        assertEquals(1, result.size());
        assertEquals(status, result.get(0).getStatus());
    }

    @Test
    void updateApplicationStatus_Valid_ShouldUpdate() {
        Application app = new Application.Builder().setStatus("Pending").build();
        when(applicationRepository.findById(1)).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.updateApplicationStatus(1, "Approved");

        assertNotNull(result);
        assertEquals("Approved", result.getStatus());
        verify(applicationRepository).save(app);
    }

    @Test
    void updateApplicationStatus_Invalid_ShouldReturnNull() {
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        Application result = applicationService.updateApplicationStatus(1, "Approved");

        assertNull(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void deleteApplication_Existing_ShouldCallDelete() {
        when(applicationRepository.existsById(1)).thenReturn(true);

        applicationService.deleteApplication(1);

        verify(applicationRepository).deleteById(1);
    }

    @Test
    void deleteApplication_NotFound_ShouldNotCallDelete() {
        when(applicationRepository.existsById(1)).thenReturn(false);

        applicationService.deleteApplication(1);

        verify(applicationRepository, never()).deleteById(anyInt());
    }
}
