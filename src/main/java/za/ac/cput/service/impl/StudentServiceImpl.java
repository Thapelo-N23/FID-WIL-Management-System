package za.ac.cput.service.impl;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.service.IStudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
        return studentRepository.save(student);
    }

    @Override
    public boolean delete(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findByActive(boolean active) {
        return studentRepository.findByActive(active);
    }

    @Override
    public boolean toggleStudentStatus(Long studentId, boolean activate) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setActive(activate);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    @Override
    public List<Student> findByStatus(String status) {
        if ("active".equalsIgnoreCase(status)) {
            return studentRepository.findByActive(true);
        } else if ("inactive".equalsIgnoreCase(status)) {
            return studentRepository.findByActive(false);
        }
        return studentRepository.findAll();
    }

    @Override
    public Long getActiveStudentCount() {
        return studentRepository.countByActive(true);
    }
}