package com.example.studentapp;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link StudentService} using a mocked repository.
 */
class StudentServiceTests {

    @Mock
    private StudentRepository repository;

    private StudentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new StudentService(repository);
    }

    @Test
    void getAllStudentsReturnsList() {
        // given repository returns two students
        List<Student> students = Arrays.asList(new Student(1L, "John", "john@test"),
                                               new Student(2L, "Jane", "jane@test"));
        when(repository.findAll()).thenReturn(students);

        // when calling service method
        List<Student> result = service.getAllStudents();

        // then list is returned
        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void getStudentByIdReturnsOptional() {
        Student student = new Student(1L, "John", "john@test");
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = service.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        verify(repository).findById(1L);
    }

    @Test
    void saveStudentPersistsEntity() {
        Student student = new Student(null, "John", "john@test");
        when(repository.save(any(Student.class))).thenReturn(new Student(1L, "John", "john@test"));

        Student saved = service.saveStudent(student);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(repository).save(captor.capture());
        assertEquals("John", captor.getValue().getName());
        assertNotNull(saved.getId());
    }

    @Test
    void deleteStudentRemovesEntity() {
        Student student = new Student(1L, "John", "john@test");

        service.deleteStudent(student);

        verify(repository).delete(student);
    }
}
