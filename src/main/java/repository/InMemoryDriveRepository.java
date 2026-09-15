package repository;

import model.PlacementDrive;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDriveRepository implements DriveRepository {

    private final Map<String, PlacementDrive> store = new ConcurrentHashMap<>();

    @Override
    public PlacementDrive save(PlacementDrive drive) {
        store.put(drive.getId(), drive);
        return drive;
    }

    @Override
    public Optional<PlacementDrive> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<PlacementDrive> findAll() {
        return new ArrayList<>(store.values());
    }
}