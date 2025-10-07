package za.ac.cput.service;

import za.ac.cput.domain.Application;
import za.ac.cput.domain.Student;
import za.ac.cput.domain.InternshipPost;

import java.util.ArrayList;

/**
 * FID WIL MANAGEMENT SYSTEM
 * IApplicationService.java
 * Author: Kelly Ngoveni
 * Date: 02 October 2025
 */

public interface IApplicationService {

    Application createApplication(Application application);
    Application getApplicationById(int id);
    ArrayList<Application> getAllApplications();
    ArrayList<Application> getApplicationsByStudent(Student student);
    ArrayList<Application> getApplicationsByPost(InternshipPost post);
    ArrayList<Application> getApplicationsByStatus(String status);
    Application updateApplicationStatus(int id, String newStatus);
    void deleteApplication(int id);
}
