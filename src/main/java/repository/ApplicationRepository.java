package repository;

import model.Application;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    Application save(Application application);
    Optional<Application> findById(String id);
    List<Application> findByStudentId(String studentId);
    
    // Crucial for preventing duplicate applications (FR-07)
    Optional<Application> findByStudentIdAndDriveId(String studentId, String driveId);
}