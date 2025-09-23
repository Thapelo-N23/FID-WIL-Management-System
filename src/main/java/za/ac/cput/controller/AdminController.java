package za.ac.cput.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.dto.CoordinatorRequest;
import za.ac.cput.dto.CoordinatorResponse;
import za.ac.cput.domain.Coordinator;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.service.CoordinatorService;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CoordinatorService coordinatorService;
    private final UserRepository userRepository;

    public AdminController(CoordinatorService coordinatorService, UserRepository userRepository) {
        this.coordinatorService = coordinatorService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register/coordinator")
    public ResponseEntity<CoordinatorResponse> registerCoordinator(@Valid @RequestBody CoordinatorRequest request,
                                                                   Principal principal) {
        String adminEmail = principal.getName();
        User adminUser = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Coordinator coordinator = coordinatorService.registerCoordinator(request, adminUser);

        CoordinatorResponse response = new CoordinatorResponse(
                coordinator.getId(),
                coordinator.getUser().getEmail(),
                coordinator.getFullName(),
                coordinator.getContactNumber(),
                coordinator.getCourseAssigned()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
