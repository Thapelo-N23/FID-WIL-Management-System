package za.ac.cput.util;

import za.ac.cput.domain.ActivityLog;
import za.ac.cput.factory.ActivityLogFactory;

import java.util.regex.Pattern;

public class Helper {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static boolean isValidEmail(String email) {
        return !isNullOrEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    public static ActivityLog logActivity(String action, String description,
                                          String performedBy, String targetUser) {
        // In real implementation, this would save to database via service
        ActivityLog log = ActivityLogFactory.createActivityLog(action, description, performedBy, targetUser);

        System.out.printf("[ACTIVITY LOG] %s | %s | By: %s | Target: %s%n",
                action, description, performedBy, targetUser);

        return log;
    }

    public static ActivityLog logActivity(String action, String description,
                                          String performedBy, String targetUser, String ipAddress) {
        ActivityLog log = ActivityLogFactory.createActivityLog(action, description, performedBy, targetUser, ipAddress);

        System.out.printf("[ACTIVITY LOG] %s | %s | By: %s | Target: %s | IP: %s%n",
                action, description, performedBy, targetUser, ipAddress);

        return log;
    }

    public static boolean validateStudentNumber(String number) {
        return number != null && number.matches("^[0-9]{9}$");
    }

    public static boolean validateStudentEmail(String email) {
        return email != null && email.matches("^[0-9]{9}@mycput\\.ac\\.za$");
    }
}