package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST endpoints for managing {@link Student} entities. Each method maps to a
 * specific HTTP operation and delegates work to {@link StudentService}.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    /** Service layer that handles repository interactions. */
    private final StudentService service;

    public StudentController(StudentService service) {
        // Inject the service layer via constructor
        this.service = service;
    }

    /**
     * Handle GET /students to list all registered students.
     */
    @GetMapping
    public List<Student> getAllStudents() {
        // Return the complete list of students
        return service.getAllStudents();
    }

    /**
     * Handle GET /students/{id} to retrieve a single student by their id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        // Fetch a single student or respond with 404
        Optional<Student> student = service.getStudentById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handle POST /students to create a new student record.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Persist the provided student
        Student saved = service.saveStudent(student);
        return ResponseEntity.ok(saved);
    }

    /**
     * Handle PUT /students/{id} to update an existing student.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        // Update an existing student with new details
        return service.getStudentById(id)
                .map(student -> {
                    // Copy over the new field values
                    student.setName(studentDetails.getName());
                    student.setEmail(studentDetails.getEmail());
                    Student updated = service.saveStudent(student);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handle DELETE /students/{id} to remove a student from the system.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        // Remove a student if present
        return service.getStudentById(id)
                .map(student -> {
                    service.deleteStudent(student);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
