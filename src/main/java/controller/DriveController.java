package controller;

import dto.ApplicationRequest;
import dto.EligibilityResult;
import model.Application;
import model.PlacementDrive;
import model.Student;
import policy.EligibilityPolicy;
import repository.CompanyRepository;
import repository.DriveRepository;
import repository.StudentRepository;
import service.ApplicationService;
import service.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class DriveController {

    private final DriveRepository driveRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;
    private final EligibilityPolicy eligibilityPolicy;
    private final ApplicationService applicationService;

    public DriveController(DriveRepository driveRepository, 
                           CompanyRepository companyRepository, 
                           StudentRepository studentRepository, 
                           EligibilityPolicy eligibilityPolicy, 
                           ApplicationService applicationService) {
        this.driveRepository = driveRepository;
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
        this.eligibilityPolicy = eligibilityPolicy;
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<PlacementDrive> createDrive(@RequestBody PlacementDrive drive) {
        // Ensure the company exists before creating a drive (Invariant requirement)
        if (companyRepository.findById(drive.getCompanyId()).isEmpty()) {
            throw new DomainException(404, "COMPANY_NOT_FOUND", "Cannot create drive. Company ID does not exist.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(driveRepository.save(drive));
    }

    @GetMapping
    public ResponseEntity<List<PlacementDrive>> getAllDrives() {
        return ResponseEntity.ok(driveRepository.findAll());
    }

    @GetMapping("/{driveId}")
    public ResponseEntity<PlacementDrive> getDrive(@PathVariable String driveId) {
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new DomainException(404, "DRIVE_NOT_FOUND", "Placement drive not found."));
        return ResponseEntity.ok(drive);
    }

    @GetMapping("/{driveId}/eligibility/{studentId}")
    public ResponseEntity<EligibilityResult> evaluateEligibility(@PathVariable String driveId, @PathVariable String studentId) {
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new DomainException(404, "DRIVE_NOT_FOUND", "Placement drive not found."));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException(404, "STUDENT_NOT_FOUND", "Student not found."));

        EligibilityResult result = eligibilityPolicy.evaluate(student, drive);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{driveId}/applications")
    public ResponseEntity<Application> applyToDrive(@PathVariable String driveId, @RequestBody ApplicationRequest request) {
        Application application = applicationService.applyToDrive(request.getStudentId(), driveId);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }
}