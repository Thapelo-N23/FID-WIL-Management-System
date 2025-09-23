package za.ac.cput.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Coordinator;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.dto.CoordinatorRequest;
import za.ac.cput.repository.CoordinatorRepository;
import za.ac.cput.repository.UserRepository;

@Service
public class CoordinatorService {

    private final UserRepository userRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final PasswordEncoder passwordEncoder;

    public CoordinatorService(UserRepository userRepository,
                              CoordinatorRepository coordinatorRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Coordinator registerCoordinator(CoordinatorRequest request, User adminUser) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.COORDINATOR);
        user.setActive(true);
        user.setEmailVerified(false);

        user = userRepository.save(user);

        Coordinator coordinator = new Coordinator();
        coordinator.setUser(user);
        coordinator.setFullName(request.getFullName());
        coordinator.setContactNumber(request.getContactNumber());
        coordinator.setCourseAssigned(request.getCourseAssigned());

        coordinator = coordinatorRepository.save(coordinator);

        sendCoordinatorEmail(user.getEmail(), request.getFullName());

        return coordinator;
    }

    private void sendCoordinatorEmail(String email, String fullName) {
        System.out.println("Email sent to " + email + ":");
        System.out.println("Hello " + fullName + ", your coordinator account has been created.");
    }
}
