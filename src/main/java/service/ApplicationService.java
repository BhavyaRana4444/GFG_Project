package service;

import model.Application;
import model.ApplicationStatus;
import model.PlacementDrive;
import model.Student;
import dto.EligibilityResult;
import policy.EligibilityPolicy;
import repository.ApplicationRepository;
import repository.DriveRepository;
import repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final DriveRepository driveRepository;
    private final EligibilityPolicy eligibilityPolicy;

    public ApplicationService(ApplicationRepository applicationRepository, 
                              StudentRepository studentRepository, 
                              DriveRepository driveRepository, 
                              EligibilityPolicy eligibilityPolicy) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
        this.eligibilityPolicy = eligibilityPolicy;
    }

    public Application applyToDrive(String studentId, String driveId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException(404, "STUDENT_NOT_FOUND", "Student not found with ID: " + studentId));
        
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new DomainException(404, "DRIVE_NOT_FOUND", "Drive not found with ID: " + driveId));

        // 1. Check Deadline
        if (LocalDate.now().isAfter(drive.getDeadline())) {
            throw new DomainException(400, "DEADLINE_PASSED", "The deadline for this drive has already passed.");
        }

        // 2. Prevent Duplicates
        if (applicationRepository.findByStudentIdAndDriveId(studentId, driveId).isPresent()) {
            throw new DomainException(409, "DUPLICATE_APPLICATION", "Student " + studentId + " has already applied to drive " + driveId + ".");
        }

        // 3. Evaluate Eligibility
        EligibilityResult result = eligibilityPolicy.evaluate(student, drive);
        if (!result.isEligible()) {
            throw new DomainException(409, "INELIGIBLE", "Student is not eligible for this drive. Reasons: " + String.join(", ", result.getReasons()));
        }

        // 4. Create and Save Application
        Application application = new Application(
                "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                studentId,
                driveId,
                ApplicationStatus.SUBMITTED,
                LocalDateTime.now()
        );
        
        return applicationRepository.save(application);
    }

    public Application updateApplicationStatus(String applicationId, ApplicationStatus newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new DomainException(404, "APPLICATION_NOT_FOUND", "Application not found."));

        ApplicationStatus currentStatus = application.getStatus();
        
        // Allowed Transition Rules
        boolean validTransition = false;
        switch (currentStatus) {
            case SUBMITTED:
                validTransition = (newStatus == ApplicationStatus.UNDER_REVIEW || newStatus == ApplicationStatus.WITHDRAWN);
                break;
            case UNDER_REVIEW:
                validTransition = (newStatus == ApplicationStatus.SHORTLISTED || newStatus == ApplicationStatus.REJECTED || newStatus == ApplicationStatus.WITHDRAWN);
                break;
            case SHORTLISTED:
                validTransition = (newStatus == ApplicationStatus.SELECTED || newStatus == ApplicationStatus.REJECTED);
                break;
            case SELECTED:
            case REJECTED:
            case WITHDRAWN:
                validTransition = false; // Terminal states
                break;
        }

        if (!validTransition) {
            throw new DomainException(409, "INVALID_TRANSITION", "Cannot transition application from " + currentStatus + " to " + newStatus);
        }

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    public Application getApplication(String id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new DomainException(404, "APPLICATION_NOT_FOUND", "Application not found."));
    }

    public List<Application> getApplicationsForStudent(String studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
}