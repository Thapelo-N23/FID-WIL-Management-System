package za.ac.cput.service;

import za.ac.cput.domain.Student;
import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student create(Student student);
    Optional<Student> read(Long id);
    Student update(Student student);
    boolean delete(Long id);
    List<Student> getAll();
    List<Student> findByActive(boolean active);

    // Fix the method signature - remove the adminEmail parameter
    boolean toggleStudentStatus(Long studentId, boolean activate);

    // Keep other methods as needed
    List<Student> findByStatus(String status);
    Long getActiveStudentCount();
}