package model;

import java.time.LocalDate;
import java.util.List;

public class PlacementDrive {

    private String id;
    private String companyId; // References the Company
    private String role;
    private String location;
    private double ctcPackage; // Package amount
    private LocalDate deadline;
    private List<String> requiredSkills;
    
    // Basic eligibility rules configured for this drive
    private double minCgpa;
    private int maxActiveBacklogs;

    public PlacementDrive() {
    }

    public PlacementDrive(String id, String companyId, String role, String location, double ctcPackage, LocalDate deadline, List<String> requiredSkills, double minCgpa, int maxActiveBacklogs) {
        this.id = id;
        this.companyId = companyId;
        this.role = role;
        this.location = location;
        this.ctcPackage = ctcPackage;
        this.deadline = deadline;
        this.requiredSkills = requiredSkills;
        this.minCgpa = minCgpa;
        this.maxActiveBacklogs = maxActiveBacklogs;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getCtcPackage() { return ctcPackage; }
    public void setCtcPackage(double ctcPackage) { this.ctcPackage = ctcPackage; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(double minCgpa) { this.minCgpa = minCgpa; }

    public int getMaxActiveBacklogs() { return maxActiveBacklogs; }
    public void setMaxActiveBacklogs(int maxActiveBacklogs) { this.maxActiveBacklogs = maxActiveBacklogs; }
}