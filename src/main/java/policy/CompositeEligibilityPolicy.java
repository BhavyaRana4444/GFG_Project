package policy;

import model.Student;
import model.PlacementDrive;
import dto.EligibilityResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompositeEligibilityPolicy implements EligibilityPolicy {

    @Override
    public EligibilityResult evaluate(Student student, PlacementDrive drive) {
        List<String> reasons = new ArrayList<>();
        boolean eligible = true;

        // Check CGPA
        if (student.getCgpa() < drive.getMinCgpa()) {
            eligible = false;
            reasons.add("Minimum CGPA required: " + drive.getMinCgpa() + "; current CGPA: " + student.getCgpa());
        }

        // Check Backlogs
        if (student.getActiveBacklogs() > drive.getMaxActiveBacklogs()) {
            eligible = false;
            reasons.add("Maximum active backlogs allowed: " + drive.getMaxActiveBacklogs() + "; current backlogs: " + student.getActiveBacklogs());
        }

        // Check Skills
        if (drive.getRequiredSkills() != null && !drive.getRequiredSkills().isEmpty()) {
            List<String> studentSkills = student.getSkills() != null ? student.getSkills() : new ArrayList<>();
            for (String requiredSkill : drive.getRequiredSkills()) {
                if (!studentSkills.contains(requiredSkill)) {
                    eligible = false;
                    reasons.add("Missing required skill: " + requiredSkill);
                }
            }
        }

        return new EligibilityResult(student.getId(), drive.getId(), eligible, reasons);
    }
}