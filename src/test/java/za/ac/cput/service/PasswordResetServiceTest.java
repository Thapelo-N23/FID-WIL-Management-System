package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.cput.domain.PasswordResetToken;
import za.ac.cput.domain.User;
import za.ac.cput.repository.PasswordResetTokenRepository;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordResetServiceTest {

    private PasswordResetTokenRepository tokenRepository;
    private UserRepository userRepository;
    private JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private PasswordResetService service;

    @BeforeEach
    void setUp() {
        tokenRepository = mock(PasswordResetTokenRepository.class);
        userRepository = mock(UserRepository.class);
        mailSender = mock(JavaMailSender.class);
        passwordEncoder = mock(PasswordEncoder.class);
        service = new PasswordResetService(tokenRepository, userRepository, mailSender, passwordEncoder);
    }

    @Test
    void testSendPasswordResetEmail_Success() {
        String email = "test@example.com";
        String baseUrl = "http://localhost:8080";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        service.sendPasswordResetEmail(email, baseUrl);

        verify(tokenRepository).deleteByUserEmail(email);
        verify(tokenRepository).save(any(PasswordResetToken.class));

        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(mailCaptor.capture());

        SimpleMailMessage sentMail = mailCaptor.getValue();
        assertTrue(sentMail.getText().contains("reset your password"));
        assertEquals(email, sentMail.getTo()[0]);
    }

    @Test
    void testResetPassword_Success() {
        String token = UUID.randomUUID().toString();
        String newPassword = "newPass123";
        PasswordResetToken resetToken = new PasswordResetToken(token, "user@example.com", LocalDateTime.now().plusMinutes(15));
        User user = new User();
        user.setEmail("user@example.com");

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(resetToken));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        boolean result = service.resetPassword(token, newPassword);

        assertTrue(result);
        verify(userRepository).save(user);
        verify(tokenRepository).delete(resetToken);
    }

    @Test
    void testResetPassword_ExpiredToken() {
        String token = "expired-token";
        PasswordResetToken expired = new PasswordResetToken(token, "test@example.com", LocalDateTime.now().minusMinutes(5));

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(expired));

        assertThrows(IllegalArgumentException.class, () -> service.resetPassword(token, "newPass123"));
        verify(tokenRepository).delete(expired);
    }
}
