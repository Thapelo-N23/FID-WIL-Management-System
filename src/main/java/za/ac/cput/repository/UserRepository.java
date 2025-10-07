package za.ac.cput.repository;

import org.springframework.stereotype.Repository;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends IRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}
