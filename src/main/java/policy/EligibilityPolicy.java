package policy;

import model.Student;
import model.PlacementDrive;
import dto.EligibilityResult;

public interface EligibilityPolicy {
    EligibilityResult evaluate(Student student, PlacementDrive drive);
}