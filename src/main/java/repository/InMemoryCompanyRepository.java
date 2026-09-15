package repository;

import model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCompanyRepository implements CompanyRepository {

    private final Map<String, Company> store = new ConcurrentHashMap<>();

    @Override
    public Company save(Company company) {
        store.put(company.getId(), company);
        return company;
    }

    @Override
    public Optional<Company> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Company> findAll() {
        return new ArrayList<>(store.values());
    }
}