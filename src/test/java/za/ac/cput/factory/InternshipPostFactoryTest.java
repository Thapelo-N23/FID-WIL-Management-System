/**
 * InternshipTrackingSystem
 * InternshipPostFactoryTest.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InternshipPostFactoryTest {

    private Business business;
    private Date futureDeadline;

//    @BeforeEach
//    void setUp() {
//        business = new Business();
//        business.setBusinessId(1);
//        business.setBusinessName("TechCorp");
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 10); // 10 days in future
//        futureDeadline = cal.getTime();
//    }

    @Test
    void createInternshipPost_success() {
        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Software Intern",
                "Assist with backend development",
                futureDeadline,
                "Open",
                business
        );

        assertNotNull(post);
        assertEquals("Software Intern", post.getTitle());
        assertEquals("Open", post.getStatus());
        assertEquals(business, post.getBusiness());
    }

    @Test
    void createInternshipPost_missingFields_fail() {
        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "",
                "",
                futureDeadline,
                "Open",
                business
        );

        assertNull(post, "Post should be null due to invalid input.");
    }

    @Test
    void createInternshipPost_pastDeadline_fail() {
        Calendar past = Calendar.getInstance();
        past.add(Calendar.DATE, -5);

        InternshipPost post = InternshipPostFactory.createInternshipPost(
                "Dev Intern",
                "Past date test",
                past.getTime(),
                "Open",
                business
        );

        assertNull(post, "Post should not be created with a past deadline.");
    }
}
