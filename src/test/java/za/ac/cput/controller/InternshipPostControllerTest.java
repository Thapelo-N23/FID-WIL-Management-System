/**
 * InternshipTrackingSystem
 * InternshipPostControllerTest.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.service.InternshipPostService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InternshipPostController.class)
class InternshipPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipPostService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Business business;
    private InternshipPost post;

    @BeforeEach
//    void setUp() {
//        business = new Business();
//        business.setBusinessId(1);
//        business.setBusinessName("TechHouse");
//
//        post = new InternshipPost.Builder()
//                .setPostId(1)
//                .setTitle("React Developer Intern")
//                .setDescription("Frontend internship opportunity")
//                .setDeadline(new Date(System.currentTimeMillis() + 86400000))
//                .setStatus("Open")
//                .setBusiness(business)
//                .build();
//    }

    @Test
    void createPost_success() throws Exception {
        when(service.create(any(), any(), any(), any(), any())).thenReturn(post);

        mockMvc.perform(post("/api/internshipPost/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("React Developer Intern"));
    }

    @Test
    void readPost_success() throws Exception {
        when(service.read(1)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/internshipPost/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("React Developer Intern"));
    }

    @Test
    void getAllPosts_success() throws Exception {
        when(service.getAll()).thenReturn(List.of(post));

        mockMvc.perform(get("/api/internshipPost/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("React Developer Intern"));
    }

    @Test
    void closePost_success() throws Exception {
        when(service.closePost(1)).thenReturn(true);

        mockMvc.perform(put("/api/internshipPost/close/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post closed successfully."));
    }

    @Test
    void deletePost_success() throws Exception {
        when(service.delete(1)).thenReturn(true);

        mockMvc.perform(delete("/api/internshipPost/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("InternshipPost deleted successfully."));
    }
}
