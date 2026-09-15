package controller;

import model.Student;
import repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DomainException;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new DomainException(409, "DUPLICATE_EMAIL", "Student with this email already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepository.save(student));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException(404, "STUDENT_NOT_FOUND", "Student not found."));
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String studentId, @RequestBody Student updatedStudent) {
        Student existing = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException(404, "STUDENT_NOT_FOUND", "Student not found."));
        
        // Update allowed fields
        existing.setName(updatedStudent.getName());
        existing.setCgpa(updatedStudent.getCgpa());
        existing.setSkills(updatedStudent.getSkills());
        existing.setActiveBacklogs(updatedStudent.getActiveBacklogs());
        
        return ResponseEntity.ok(studentRepository.save(existing));
    }
}