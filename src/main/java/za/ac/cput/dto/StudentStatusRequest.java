package za.ac.cput.dto;

public class StudentStatusRequest {
    private boolean activate;
    private String adminEmail;

    public StudentStatusRequest() {}

    public StudentStatusRequest(boolean activate, String adminEmail) {
        this.activate = activate;
        this.adminEmail = adminEmail;
    }

    // Getters and Setters
    public boolean isActivate() { return activate; }
    public void setActivate(boolean activate) { this.activate = activate; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
}