package za.ac.cput.service;

import za.ac.cput.domain.Student;
import java.util.List;
import java.util.Optional;

public interface IStudentService extends IService<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);
    List<Student> findByActive(Boolean active);
    Optional<Student> findByUserEmail(String email);
    boolean toggleStudentStatus(Long studentId, String adminEmail, boolean activate);
    List<Student> findByStatus(String status);
}