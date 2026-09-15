package dto;

import java.util.List;

public class EligibilityResult {
    
    private String studentId;
    private String driveId;
    private boolean eligible;
    private List<String> reasons;

    public EligibilityResult() {
    }

    public EligibilityResult(String studentId, String driveId, boolean eligible, List<String> reasons) {
        this.studentId = studentId;
        this.driveId = driveId;
        this.eligible = eligible;
        this.reasons = reasons;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDriveId() { return driveId; }
    public void setDriveId(String driveId) { this.driveId = driveId; }

    public boolean isEligible() { return eligible; }
    public void setEligible(boolean eligible) { this.eligible = eligible; }

    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}