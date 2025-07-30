package com.example.studentapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Simple JPA entity representing a student.
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    private String name;  // student's full name
    private String email; // contact email

    public Student() {
        // Default constructor needed by JPA
    }

    public Student(Long id, String name, String email) {
        // Convenient constructor for manual instantiation
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        // Unique identifier for the student
        return id;
    }

    public void setId(Long id) {
        // Setter used by JPA and tests
        this.id = id;
    }

    public String getName() {
        // Retrieve the full name
        return name;
    }

    public void setName(String name) {
        // Update the student's name
        this.name = name;
    }

    public String getEmail() {
        // Access the contact email address
        return email;
    }

    public void setEmail(String email) {
        // Set the contact email address
        this.email = email;
    }
}
