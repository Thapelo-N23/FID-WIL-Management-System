package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Coordinator;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
}
