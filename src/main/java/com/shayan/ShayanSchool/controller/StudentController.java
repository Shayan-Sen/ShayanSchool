package com.shayan.ShayanSchool.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanSchool.model.Student;
import com.shayan.ShayanSchool.model.StudentRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudents() {
        ApiResponse response = new ApiResponse();
        try {
            List<Student> students = repository.findAll();


            response.setMessage("Successfully extracted all student records");
            response.setData(students);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("An error occurred while retrieving students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable String id) {
        ApiResponse response = new ApiResponse();
        try {
            Student student = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

            response.setMessage("Successfully retrieved student with ID: " + id);
            response.setData(student);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStudent(@RequestBody Student student) {
        ApiResponse response = new ApiResponse();
        try {
            repository.save(student);

            response.setMessage("Successfully created new student");
            response.setData(student);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Failed to create student. Please check the input data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@RequestBody Student student,
            @PathVariable String id) {
        ApiResponse response = new ApiResponse();
        try {
            Student existingStudent = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cannot update. Student not found with ID: " + id));
            existingStudent.setName(student.getName());
            existingStudent.setRollNo(student.getRollNo());
            existingStudent.setRegistrationNo(student.getRegistrationNo());
            existingStudent.setCgpa(student.getCgpa());

            response.setData(student);
            response.setMessage("Student updated successfully!");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot delete. Student not found with ID: " + id);
        }
    }
}
