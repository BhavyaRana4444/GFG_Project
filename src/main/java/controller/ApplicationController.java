package controller;

import dto.StatusUpdateRequest;
import model.Application;
import service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Mapped explicitly to match the required API contract path for student applications
    @GetMapping("/api/students/{studentId}/applications")
    public ResponseEntity<List<Application>> getStudentApplications(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsForStudent(studentId));
    }

    @GetMapping("/api/applications/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable String applicationId) {
        return ResponseEntity.ok(applicationService.getApplication(applicationId));
    }

    @PatchMapping("/api/applications/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable String applicationId, @RequestBody StatusUpdateRequest request) {
        Application updatedApp = applicationService.updateApplicationStatus(applicationId, request.getStatus());
        return ResponseEntity.ok(updatedApp);
    }
}