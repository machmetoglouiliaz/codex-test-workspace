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

    /** Repository used for data persistence. */
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        // Constructor injection promotes immutability

        this.repository = repository;
    }

    /**
     * Retrieve all students from the database.
     */
    public List<Student> getAllStudents() {
        // Retrieve every Student record

        return repository.findAll();
    }

    /**
     * Find a student by id.
     */
    public Optional<Student> getStudentById(Long id) {
        // Look up a student by its primary key

        return repository.findById(id);
    }

    /**
     * Save a student to the database.
     */
    public Student saveStudent(Student student) {
        // Persist the student entity

        return repository.save(student);
    }

    /**
     * Delete the provided student.
     */
    public void deleteStudent(Student student) {
        // Remove the student from the database

        repository.delete(student);
    }
}
