package za.ac.cput.repository;

import za.ac.cput.domain.Coordinator;
import java.util.Optional;

public interface CoordinatorRepository extends IRepository<Coordinator, Long> {
    Optional<Coordinator> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
}