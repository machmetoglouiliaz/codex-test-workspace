package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Provides CRUD operations for {@link Student} entities.
 */
@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve all students from the database.
     */
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    /**
     * Find a student by id.
     */
    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    /**
     * Save a student to the database.
     */
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    /**
     * Delete the provided student.
     */
    public void deleteStudent(Student student) {
        repository.delete(student);
    }
}
