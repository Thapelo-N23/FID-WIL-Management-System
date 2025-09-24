package za.ac.cput.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Coordinator;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.dto.CoordinatorRequest;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.CoordinatorRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.service.ICoordinatorService;
import za.ac.cput.util.Helper;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinatorServiceImpl implements ICoordinatorService {

    private final UserRepository userRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final PasswordEncoder passwordEncoder;

    public CoordinatorServiceImpl(UserRepository userRepository,
                                  CoordinatorRepository coordinatorRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Coordinator registerCoordinator(CoordinatorRequest request, String adminEmail) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        User user = UserFactory.createCoordinator(request.getEmail(), request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        Coordinator coordinator = new Coordinator.Builder()
                .setUser(user)
                .setFullName(request.getFullName())
                .setContactNumber(request.getContactNumber())
                .setCourseAssigned(request.getCourseAssigned())
                .build();

        coordinator = coordinatorRepository.save(coordinator);

        Helper.logActivity("COORDINATOR_REGISTERED",
                "Coordinator " + request.getFullName() + " registered by admin",
                adminEmail, request.getEmail());

        return coordinator;
    }

    @Override
    public Coordinator create(Coordinator entity) {
        return coordinatorRepository.save(entity);
    }

    @Override
    public Optional<Coordinator> read(Long id) {
        return coordinatorRepository.findById(id);
    }

    @Override
    public Coordinator update(Coordinator entity) {
        if (!coordinatorRepository.existsById(entity.getId())) {
            throw new IllegalArgumentException("Coordinator not found with id: " + entity.getId());
        }
        return coordinatorRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        if (!coordinatorRepository.existsById(id)) {
            return false;
        }
        coordinatorRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Coordinator> getAll() {
        return coordinatorRepository.findAll();
    }
}