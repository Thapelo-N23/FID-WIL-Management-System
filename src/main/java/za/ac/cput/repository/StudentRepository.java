package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);
    List<Student> findByActive(Boolean active);
    Optional<Student> findByUserEmail(String email);
    List<Student> findByStatus(String status);
    long countByActive(boolean active);

}