package za.ac.cput.dto;

public class CoordinatorResponse {
    private Long id;
    private String email;
    private String fullName;
    private String contactNumber;
    private String courseAssigned;

    public CoordinatorResponse() {}

    public CoordinatorResponse(Long id, String email, String fullName, String contactNumber, String courseAssigned) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.courseAssigned = courseAssigned;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getCourseAssigned() { return courseAssigned; }
    public void setCourseAssigned(String courseAssigned) { this.courseAssigned = courseAssigned; }
}
