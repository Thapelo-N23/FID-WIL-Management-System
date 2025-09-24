package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.service.IStudentService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@CrossOrigin(origins = "*")
public class AdminStudentController {

    private final IStudentService studentService;

    public AdminStudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/active")
    public List<Student> getActiveStudents() {
        return studentService.findByActive(true);
    }

    @GetMapping("/inactive")
    public List<Student> getInactiveStudents() {
        return studentService.findByActive(false);
    }

    @PatchMapping("/{studentId}/activate")
    public ResponseEntity<String> activateStudent(@PathVariable Long studentId) {
        boolean success = studentService.toggleStudentStatus(studentId, true);
        if (success) {
            return ResponseEntity.ok("Student activated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    @PatchMapping("/{studentId}/deactivate")
    public ResponseEntity<String> deactivateStudent(@PathVariable Long studentId) {
        boolean success = studentService.toggleStudentStatus(studentId, false);
        if (success) {
            return ResponseEntity.ok("Student deactivated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        return studentService.read(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Student> getStudentsByStatus(@PathVariable String status) {
        return studentService.findByStatus(status);
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveStudentCount() {
        return ResponseEntity.ok(studentService.getActiveStudentCount());
    }
}