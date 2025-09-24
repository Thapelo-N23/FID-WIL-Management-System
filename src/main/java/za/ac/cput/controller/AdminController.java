package za.ac.cput.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.dto.CoordinatorRequest;
import za.ac.cput.dto.CoordinatorResponse;
import za.ac.cput.domain.Coordinator;
import za.ac.cput.service.ICoordinatorService;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ICoordinatorService coordinatorService;

    public AdminController(ICoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @PostMapping("/register/coordinator")
    public ResponseEntity<CoordinatorResponse> registerCoordinator(
            @Valid @RequestBody CoordinatorRequest request,
            Principal principal) {
        // Handle null principal (if not secured)
        String adminEmail = (principal != null) ? principal.getName() : "system";

        Coordinator coordinator = coordinatorService.registerCoordinator(request, adminEmail);

        CoordinatorResponse response = new CoordinatorResponse(
                coordinator.getId(),
                coordinator.getUser() != null ? coordinator.getUser().getEmail() : null,
                coordinator.getFullName(),
                coordinator.getContactNumber(),
                coordinator.getCourseAssigned()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}