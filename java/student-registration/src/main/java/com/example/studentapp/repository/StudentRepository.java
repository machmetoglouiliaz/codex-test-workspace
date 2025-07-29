package com.example.studentapp.repository;

import com.example.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link Student} entities.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // additional query methods can be defined here

}
