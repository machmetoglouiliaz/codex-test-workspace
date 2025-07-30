package com.example.studentapp;

import com.example.studentapp.controller.StudentController;
import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link StudentController} using {@link WebMvcTest}.
 * The service layer is mocked so only controller logic is exercised.
 */
@WebMvcTest(StudentController.class)
class StudentControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStudentsReturnsList() throws Exception {
        when(service.getAllStudents()).thenReturn(
                Arrays.asList(new Student(1L, "John", "john@test")));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void createStudentReturnsSavedEntity() throws Exception {
        Student input = new Student(null, "John", "john@test");
        Student saved = new Student(1L, "John", "john@test");
        when(service.saveStudent(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateStudentUpdatesExisting() throws Exception {
        Student existing = new Student(1L, "John", "john@test");
        Student updated = new Student(1L, "Johnny", "john@test");
        when(service.getStudentById(1L)).thenReturn(Optional.of(existing));
        when(service.saveStudent(any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Johnny"));
    }

    @Test
    void deleteStudentRemovesEntity() throws Exception {
        Student existing = new Student(1L, "John", "john@test");
        when(service.getStudentById(1L)).thenReturn(Optional.of(existing));

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());

        verify(service).deleteStudent(existing);
    }
}
