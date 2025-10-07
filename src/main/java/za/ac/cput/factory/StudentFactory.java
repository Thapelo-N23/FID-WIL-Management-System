package za.ac.cput.factory;

import za.ac.cput.domain.Student;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class StudentFactory {


    public static Student createStudent(User user, String studentNumber, String fullName,
                                        String contactNumber, String course, String cvFilePath) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (Helper.isNullOrEmpty(studentNumber)) {
            throw new IllegalArgumentException("Student number is required");
        }
        if (Helper.isNullOrEmpty(fullName)) {
            throw new IllegalArgumentException("Full name is required");
        }

        return new Student.Builder()
                .setUser(user)
                .setStudentNumber(studentNumber.trim())
                .setFullName(fullName.trim())
                .setContactNumber(contactNumber != null ? contactNumber.trim() : null)
                .setCourse(course != null ? course.trim() : null)
                .setCvFilePath(cvFilePath != null ? cvFilePath.trim() : null)
                .build();
    }


    public static Student createMinimalStudent(User user, String studentNumber, String fullName) {
        return createStudent(user, studentNumber, fullName, null, null, null);
    }


}
