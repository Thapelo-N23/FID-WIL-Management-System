/**
 * InternshipTrackingSystem
 * InternshipPostServiceTest.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.repository.InternshipPostRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InternshipPostServiceTest {

    @Mock
    private InternshipPostRepository repository;

    @InjectMocks
    private InternshipPostService service;

    private Business business;
    private InternshipPost post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

//        business = new Business();
//        business.setBusinessId(1);
//        business.setBusinessName("FutureTech");

        post = new InternshipPost.Builder()
                .setPostId(1)
                .setTitle("Java Intern")
                .setDescription("Assist with Spring Boot projects")
                .setDeadline(new Date(System.currentTimeMillis() + 86400000))
                .setStatus("Open")
                .setBusiness(business)
                .build();
    }

    @Test
    void create_success() {
        when(repository.save(any(InternshipPost.class))).thenReturn(post);

        InternshipPost created = service.create(
                "Java Intern",
                "Assist with Spring Boot projects",
                new Date(System.currentTimeMillis() + 86400000),
                "Open",
                business
        );

        assertNotNull(created);
        assertEquals("Java Intern", created.getTitle());
    }

    @Test
    void read_success() {
        when(repository.findById(1)).thenReturn(Optional.of(post));

        Optional<InternshipPost> found = service.read(1);
        assertTrue(found.isPresent());
        assertEquals("Java Intern", found.get().getTitle());
    }

    @Test
    void closePost_success() {
        when(repository.findById(1)).thenReturn(Optional.of(post));
        when(repository.save(any(InternshipPost.class))).thenReturn(post);

        boolean result = service.closePost(1);
        assertTrue(result);
        verify(repository, times(1)).save(any(InternshipPost.class));
    }

    @Test
    void delete_success() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        boolean deleted = service.delete(1);
        assertTrue(deleted);
        verify(repository, times(1)).deleteById(1);
    }
}
