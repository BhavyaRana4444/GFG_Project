package dto;

public class ChatRequest {
    private String studentId;
    private String driveId;
    private String message;

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getDriveId() { return driveId; }
    public void setDriveId(String driveId) { this.driveId = driveId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}