/**
 * InternshipTrackingSystem
 * InternshipPostFactory.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.factory;

import za.ac.cput.domain.Business;
import za.ac.cput.domain.InternshipPost;
import za.ac.cput.util.Helper;

import java.util.Date;

/**
 * Factory class for creating InternshipPost entities.
 * Ensures validation and domain integrity.
 */
public class InternshipPostFactory {

    /**
     * Creates an InternshipPost instance with validation.
     *
     * @param title       - The title of the internship post
     * @param description - Description of the post
     * @param deadline    - Application deadline date
     * @param status      - Current status (e.g., Open, Closed)
     * @param business    - Associated Business entity
     * @return InternshipPost object if valid, else null
     */
    public static InternshipPost createInternshipPost(String title,
                                                      String description,
                                                      Date deadline,
                                                      String status,
                                                      Business business) {

        // Validation logic
        if (Helper.isNullOrEmpty(title) || Helper.isNullOrEmpty(description) || business == null) {
            System.out.println(" InternshipPost creation failed: Missing required fields.");
            return null;
        }

        if (deadline == null || deadline.before(new Date())) {
            System.out.println(" Invalid deadline: It cannot be null or in the past.");
            return null;
        }

        if (Helper.isNullOrEmpty(status)) {
            status = "Open"; // Default value
        }

        // Build the InternshipPost using the Builder pattern
        return new InternshipPost.Builder()
                .setTitle(title.trim())
                .setDescription(description.trim())
                .setDeadline(deadline)
                .setStatus(status.trim())
                .setBusiness(business)
                .build();
    }
}

