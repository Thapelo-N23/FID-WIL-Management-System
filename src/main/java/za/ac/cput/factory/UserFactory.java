package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.util.Helper;

public class UserFactory {

    public static User createUser(String email, String password, Role role) {
        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        if (Helper.isNullOrEmpty(password) || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        User user = new User();
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(password);
        user.setRole(role);
        user.setActive(true);
        user.setEmailVerified(false);
        user.setPopiaConsent(false);

        return user;
    }

    public static User createStudent(String email, String password) {
        return createUser(email, password, Role.STUDENT);
    }

    public static User createBusiness(String email, String password) {
        return createUser(email, password, Role.BUSINESS);
    }

    public static User createCoordinator(String email, String password) {
        return createUser(email, password, Role.COORDINATOR);
    }

    public static User createAdmin(String email, String password) {
        return createUser(email, password, Role.ADMIN);
    }
}