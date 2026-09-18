package repository;

import model.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryApplicationRepository implements ApplicationRepository {

    private final Map<String, Application> store = new ConcurrentHashMap<>();

    @Override
    public Application save(Application application) {
        store.put(application.getId(), application);
        return application;
    }

    @Override
    public Optional<Application> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Application> findByStudentId(String studentId) {
        return store.values().stream()
                .filter(app -> app.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Application> findByStudentIdAndDriveId(String studentId, String driveId) {
        return store.values().stream()
                .filter(app -> app.getStudentId().equals(studentId) && app.getDriveId().equals(driveId))
                .findFirst();
    }
}