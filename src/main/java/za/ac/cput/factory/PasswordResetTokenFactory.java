package za.ac.cput.factory;

import za.ac.cput.domain.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetTokenFactory {

    public static PasswordResetToken createToken(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        return new PasswordResetToken(token, userEmail, expiry);
    }

    public static PasswordResetToken createToken(String token, String email, LocalDateTime expiry) {


        return null;
    }
}

