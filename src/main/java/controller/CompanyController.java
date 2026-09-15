package controller;

import model.Company;
import repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DomainException;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        if (companyRepository.findById(company.getId()).isPresent()) {
            throw new DomainException(409, "DUPLICATE_COMPANY", "Company ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(companyRepository.save(company));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable String companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new DomainException(404, "COMPANY_NOT_FOUND", "Company not found."));
        return ResponseEntity.ok(company);
    }
}