package za.ac.cput.service;

import za.ac.cput.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IService<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(za.ac.cput.domain.Role role);
    boolean deactivateUser(Long userId, String adminEmail);
    boolean activateUser(Long userId, String adminEmail);
}