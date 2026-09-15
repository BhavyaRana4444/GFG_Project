package model;

import java.util.List;

public class Student {
    
    private String id;
    private String name;
    private String email;
    private String programme;
    private int graduationYear;
    private double cgpa;
    private int activeBacklogs;
    private List<String> skills;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(String id, String name, String email, String programme, int graduationYear, double cgpa, int activeBacklogs, List<String> skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.programme = programme;
        this.graduationYear = graduationYear;
        this.cgpa = cgpa;
        this.activeBacklogs = activeBacklogs;
        this.skills = skills;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProgramme() { return programme; }
    public void setProgramme(String programme) { this.programme = programme; }

    public int getGraduationYear() { return graduationYear; }
    public void setGraduationYear(int graduationYear) { this.graduationYear = graduationYear; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    public int getActiveBacklogs() { return activeBacklogs; }
    public void setActiveBacklogs(int activeBacklogs) { this.activeBacklogs = activeBacklogs; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}