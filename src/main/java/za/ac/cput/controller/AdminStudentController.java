package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.service.IStudentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
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
    public ResponseEntity<String> activateStudent(@PathVariable Long studentId, Principal principal) {
        boolean success = studentService.toggleStudentStatus(studentId, principal.getName(), true);
        if (success) {
            return ResponseEntity.ok("Student activated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    @PatchMapping("/{studentId}/deactivate")
    public ResponseEntity<String> deactivateStudent(@PathVariable Long studentId, Principal principal) {
        boolean success = studentService.toggleStudentStatus(studentId, principal.getName(), false);
        if (success) {
            return ResponseEntity.ok("Student deactivated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    @GetMapping("/status/{status}")
    public List<Student> getStudentsByStatus(@PathVariable String status) {
        return studentService.findByStatus(status);
    }
}