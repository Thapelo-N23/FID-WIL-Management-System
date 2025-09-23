package za.ac.cput.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CoordinatorRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String contactNumber;

    @NotBlank(message = "Course assigned is required")
    private String courseAssigned;

    public CoordinatorRequest() {}

    public CoordinatorRequest(String email, String password, String fullName, String contactNumber, String courseAssigned) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.courseAssigned = courseAssigned;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getCourseAssigned() { return courseAssigned; }
    public void setCourseAssigned(String courseAssigned) { this.courseAssigned = courseAssigned; }
}
