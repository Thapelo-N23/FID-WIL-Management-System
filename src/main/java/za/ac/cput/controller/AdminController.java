package za.ac.cput.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.dto.CoordinatorRequest;
import za.ac.cput.dto.CoordinatorResponse;
import za.ac.cput.domain.Coordinator;
import za.ac.cput.service.ICoordinatorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final ICoordinatorService coordinatorService;

    public AdminController(ICoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @PostMapping("/register/coordinator")
    public ResponseEntity<?> registerCoordinator(
            @Valid @RequestBody CoordinatorRequest request,
            @RequestHeader(value = "Admin-Email", required = false) String adminEmail) {

        try {
            // Use provided admin email or default - no authentication required
            String effectiveAdminEmail = (adminEmail != null && !adminEmail.isEmpty())
                    ? adminEmail : "admin@cput.ac.za";

            Coordinator coordinator = coordinatorService.registerCoordinator(request, effectiveAdminEmail);

            CoordinatorResponse response = new CoordinatorResponse(
                    coordinator.getId(),
                    coordinator.getUser() != null ? coordinator.getUser().getEmail() : null,
                    coordinator.getFullName(),
                    coordinator.getContactNumber(),
                    coordinator.getCourseAssigned()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering coordinator: " + e.getMessage());
        }
    }

    @GetMapping("/coordinators")
    public ResponseEntity<List<CoordinatorResponse>> getAllCoordinators() {
        try {
            List<Coordinator> coordinators = coordinatorService.getAll();
            List<CoordinatorResponse> responses = coordinators.stream()
                    .map(coordinator -> new CoordinatorResponse(
                            coordinator.getId(),
                            coordinator.getUser() != null ? coordinator.getUser().getEmail() : null,
                            coordinator.getFullName(),
                            coordinator.getContactNumber(),
                            coordinator.getCourseAssigned()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/coordinators/{id}")
    public ResponseEntity<String> deleteCoordinator(@PathVariable Long id) {
        try {
            // Simplified - just delete without admin authentication
            boolean deleted = coordinatorService.delete(id);
            if (deleted) {
                return ResponseEntity.ok("Coordinator deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coordinator not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting coordinator: " + e.getMessage());
        }
    }
}