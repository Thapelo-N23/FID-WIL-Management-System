package za.ac.cput.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      StudentRepository studentRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if students already exist
            if (studentRepository.count() == 0) {
                System.out.println("Creating sample students...");

                // Create sample students using the Builder pattern
                createSampleStudent(userRepository, studentRepository, passwordEncoder,
                        "219123456", "John Student", "219123456@mycput.ac.za", "IT Diploma", true);

                createSampleStudent(userRepository, studentRepository, passwordEncoder,
                        "219123457", "Sarah Learner", "219123457@mycput.ac.za", "Software Development", true);

                createSampleStudent(userRepository, studentRepository, passwordEncoder,
                        "219123458", "Mike Graduate", "219123458@mycput.ac.za", "Graphic Design", false);

                createSampleStudent(userRepository, studentRepository, passwordEncoder,
                        "219123459", "Lisa Scholar", "219123459@mycput.ac.za", "Business Management", true);

                createSampleStudent(userRepository, studentRepository, passwordEncoder,
                        "221602011", "Sanele Zondi", "221602011@mycput.ac.za", "IT Diploma", true);

                System.out.println("Sample students created successfully!");
            } else {
                System.out.println("Students already exist in database. Skipping sample data creation.");
            }
        };
    }

    private void createSampleStudent(UserRepository userRepository,
                                     StudentRepository studentRepository,
                                     PasswordEncoder passwordEncoder,
                                     String studentNumber, String fullName,
                                     String email, String course, boolean active) {

        // Check if user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("User already exists: " + email);
            return;
        }

        // Create user account for student
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("student123")); // Default password
        user.setRole(Role.STUDENT);
        user.setActive(true);
        User savedUser = userRepository.save(user);

        // Create student profile using Builder pattern (correct way)
        Student student = new Student.Builder()
                .setUser(savedUser)
                .setStudentNumber(studentNumber)
                .setFullName(fullName)
                .setCourse(course)
                .setActive(active)
                .setStatus("UNPLACED")
                .build();

        studentRepository.save(student);
        System.out.println("Created student: " + fullName + " (" + studentNumber + ")");
    }
}