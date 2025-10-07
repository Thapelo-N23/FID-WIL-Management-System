package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.PasswordResetToken;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenFactoryTest {

    @Test
    void testCreatePasswordResetToken_Success() {
        String email = "user@example.com";
        String token = "abc123";
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = PasswordResetTokenFactory.createToken(token, email, expiry);

        assertNotNull(resetToken);
        assertEquals(email, resetToken.getUserEmail());
        assertEquals(token, resetToken.getToken());
        assertEquals(expiry, resetToken.getExpiryDate());
    }

    @Test
    void testCreatePasswordResetToken_InvalidData() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PasswordResetTokenFactory.createToken(null, "", LocalDateTime.now());
        });
        assertTrue(exception.getMessage().contains("invalid"));
    }
}
