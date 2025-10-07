package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.User;
import za.ac.cput.domain.InternshipPost;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationFactoryTest {

    // Helper to create a future date for InternshipPost deadline
    private Date getFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // 7 days from now
        return cal.getTime();
    }

    @Test
    void createApplication_ValidInput_ShouldCreateApplication() {
        // Arrange
        User user = new User.Builder().setEmail("student@example.com").setPassword("password").build();
        Student student = StudentFactory.createMinimalStudent(user, "S12345", "Kelly Ngoveni");

        Business business = new Business.Builder()
                .setCompanyName("TechCorp")
                .setRegistrationNumber("REG123")
                .setEmail("contact@techcorp.com")
                .build();

        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Software Intern",
                "Learn Java development",
                getFutureDate(),
                "Open",
                business
        );

        String status = "Pending";

        // Act
        Application application = ApplicationFactory.createApplication(student, post, status);

        // Assert
        assertNotNull(application, "Application should not be null");
        assertEquals(student, application.getStudent());
        assertEquals(post, application.getPost());
        assertEquals(status, application.getStatus());
        assertNotNull(application.getCreatedAt(), "CreatedAt should be set");
        assertNotNull(application.getUpdatedAt(), "UpdatedAt should be set");
    }

    @Test
    void createApplication_NullStudent_ShouldReturnNull() {
        Business business = new Business.Builder()
                .setCompanyName("TechCorp")
                .setRegistrationNumber("REG123")
                .setEmail("contact@techcorp.com")
                .build();

        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Software Intern",
                "Learn Java development",
                getFutureDate(),
                "Open",
                business
        );

        Application application = ApplicationFactory.createApplication(null, post, "Pending");

        assertNull(application, "Application should be null when student is null");
    }

    @Test
    void createApplication_NullPost_ShouldReturnNull() {
        User user = new User.Builder().setEmail("student@example.com").setPassword("password").build();
        Student student = StudentFactory.createMinimalStudent(user, "S12345", "John Doe");

        Application application = ApplicationFactory.createApplication(student, null, "Pending");

        assertNull(application, "Application should be null when post is null");
    }

    @Test
    void createApplication_EmptyStatus_ShouldReturnNull() {
        User user = new User.Builder().setEmail("student@example.com").setPassword("password").build();
        Student student = StudentFactory.createMinimalStudent(user, "S12345", "John Doe");

        Business business = new Business.Builder()
                .setCompanyName("TechCorp")
                .setRegistrationNumber("REG123")
                .setEmail("contact@techcorp.com")
                .build();

        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Software Intern",
                "Learn Java development",
                getFutureDate(),
                "Open",
                business
        );

        Application application = ApplicationFactory.createApplication(student, post, "  ");

        assertNull(application, "Application should be null when status is empty");
    }

    @Test
    void createApplication_NullStatus_ShouldReturnNull() {
        User user = new User.Builder().setEmail("student@example.com").setPassword("password").build();
        Student student = StudentFactory.createMinimalStudent(user, "S12345", "Kelly Ngoveni");

        Business business = new Business.Builder()
                .setCompanyName("TechCorp")
                .setRegistrationNumber("REG123")
                .setEmail("contact@techcorp.com")
                .build();

        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Software Intern",
                "Learn Java development",
                getFutureDate(),
                "Open",
                business
        );

        Application application = ApplicationFactory.createApplication(student, post, null);

        assertNull(application, "Application should be null when status is null");
    }
}
