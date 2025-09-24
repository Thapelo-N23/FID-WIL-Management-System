// File: StudentController.java (temporary for testing)
package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.User;
import za.ac.cput.service.IStudentService;
import za.ac.cput.service.IUserService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final IStudentService studentService;
    private final IUserService userService;

    public StudentController(IStudentService studentService, IUserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // For testing purposes - create a simple student
        Student created = studentService.create(student);
        return ResponseEntity.ok(created);
    }
}