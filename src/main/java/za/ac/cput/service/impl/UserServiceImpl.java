// UserServiceImpl.java
package za.ac.cput.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ActivityLog;
import za.ac.cput.domain.User;
import za.ac.cput.factory.ActivityLogFactory;
import za.ac.cput.repository.ActivityLogRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.service.IUserService;
import za.ac.cput.util.Helper;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;

    public UserServiceImpl(UserRepository userRepository, ActivityLogRepository activityLogRepository) {
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public User create(User user) {
        Helper.validateEmail(user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findByRole(za.ac.cput.domain.Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional
    public boolean deactivateUser(Long userId, String adminEmail) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(false);
            userRepository.save(user);

            // Save activity log to database
            ActivityLog log = ActivityLogFactory.createActivityLog(
                    "USER_DEACTIVATED",
                    "User " + user.getEmail() + " deactivated by admin",
                    adminEmail,
                    user.getEmail()
            );
            activityLogRepository.save(log);

            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean activateUser(Long userId, String adminEmail) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(true);
            userRepository.save(user);

            // Log activity
            Helper.logActivity("USER_ACTIVATED",
                    "User " + user.getEmail() + " activated by " + adminEmail,
                    adminEmail, user.getEmail());
            return true;
        }
        return false;
    }
}