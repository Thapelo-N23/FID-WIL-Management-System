package za.ac.cput.factory;

import za.ac.cput.domain.Application;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.InternshipPost;

import java.time.LocalDateTime;

/**
 * FID WIL MANAGEMENT SYSTEM
 * ApplicationFactory.java
 * Author: Kelly Ngoveni
 * Date: 02 October 2025
 */

public class ApplicationFactory {

    public static Application createApplication(Student student, InternshipPost post, String status) {
        if (student == null || post == null || status == null || status.trim().isEmpty()) {
            System.out.println("Invalid Application input: one or more required fields are null or empty");
            return null;
        }

        return new Application.Builder()
                .setStudent(student)
                .setPost(post)
                .setStatus(status.trim())
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now())
                .build();
    }
}
