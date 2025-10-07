package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Application;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.InternshipPost;

import java.util.List;

/**
 * FID WIL MANAGEMENT SYSTEM
 * ApplicationRepository.java
 * Author: Kelly Ngoveni
 * Date: 02 October 2025
 */

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByStudent(Student student);

    List<Application> findByPost(InternshipPost post);

    List<Application> findByStatus(String status);
}
