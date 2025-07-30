package com.example.studentapp;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the REST controller using MockMvc and an in-memory database.
 */
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    void createAndRetrieveStudent() throws Exception {
        Student student = new Student(null, "John", "john@test");

        // create
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John"));

        // retrieve list
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void updateStudent() throws Exception {
        Student saved = repository.save(new Student(null, "John", "john@test"));
        saved.setName("Johnny");

        mockMvc.perform(put("/students/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Johnny"));
    }

    @Test
    void deleteStudent() throws Exception {
        Student saved = repository.save(new Student(null, "John", "john@test"));

        mockMvc.perform(delete("/students/" + saved.getId()))
                .andExpect(status().isNoContent());
    }
}
