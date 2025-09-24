package za.ac.cput.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ActivityLog;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.ActivityLogFactory;
import za.ac.cput.repository.ActivityLogRepository;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.service.IStudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private final ActivityLogRepository activityLogRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ActivityLogRepository activityLogRepository) {
        this.studentRepository = studentRepository;
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> read(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student update(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }

    @Override
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber);
    }

    @Override
    public List<Student> findByActive(Boolean active) {
        return studentRepository.findByActive(active);
    }

    @Override
    public Optional<Student> findByUserEmail(String email) {
        return studentRepository.findByUserEmail(email);
    }

    @Override
    @Transactional
    public boolean toggleStudentStatus(Long studentId, String adminEmail, boolean activate) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setActive(activate);
            studentRepository.save(student);

            // Log the activity
            String action = activate ? "STUDENT_ACTIVATED" : "STUDENT_DEACTIVATED";
            String description = "Student " + student.getFullName() +
                    " (" + student.getStudentNumber() + ") " +
                    (activate ? "activated" : "deactivated") + " by admin";

            ActivityLog log = ActivityLogFactory.createActivityLog(
                    action, description, adminEmail, student.getUser().getEmail()
            );
            activityLogRepository.save(log);

            return true;
        }
        return false;
    }

    @Override
    public List<Student> findByStatus(String status) {
        return studentRepository.findByStatus(status);
    }
}