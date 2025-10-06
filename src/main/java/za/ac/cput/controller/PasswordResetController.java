package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.PasswordResetService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        String baseUrl = "http://localhost:8080";
        passwordResetService.sendPasswordResetEmail(email, baseUrl);
        return ResponseEntity.ok("Password reset link sent to email: " + email);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok("Password has been reset successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired token");
    }
}
