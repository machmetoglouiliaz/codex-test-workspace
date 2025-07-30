package com.example.studentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point for the Student Registration system.
 */
@SpringBootApplication // enables component scanning and auto-configuration
public class StudentRegistrationApplication {

    public static void main(String[] args) {
        // Bootstrap the Spring Boot application
        SpringApplication.run(StudentRegistrationApplication.class, args);
    }
}
