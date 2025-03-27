package com.shayan.ShayanSchool.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private final StudentAssembler assembler;

    @GetMapping
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Student>>>> getAllStudents() {
        ApiResponse<CollectionModel<EntityModel<Student>>> response = new ApiResponse<>();
        try {
            List<EntityModel<Student>> students = repository.findAll().stream().map(assembler::toModel)
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<Student>> studentModel = CollectionModel.of(students,
                    linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel());

            response.setMessage("Successfully extracted all student records");
            response.setData(studentModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("An error occurred while retrieving students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EntityModel<Student>>> getStudentById(@PathVariable UUID id) {
        ApiResponse<EntityModel<Student>> response = new ApiResponse<>();
        try {
            Student student = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
            EntityModel<Student> studentModel = assembler.toModel(student);

            response.setMessage("Successfully retrieved student with ID: " + id);
            response.setData(studentModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EntityModel<Student>>> createStudent(@RequestBody Student student) {
        ApiResponse<EntityModel<Student>> response = new ApiResponse<>();
        try {
            repository.save(student);
            EntityModel<Student> studentModel = assembler.toModel(student);

            response.setMessage("Successfully created new student");
            response.setData(studentModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Failed to create student. Please check the input data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EntityModel<Student>>> updateStudent(@RequestBody Student student,
            @PathVariable UUID id) {
        ApiResponse<EntityModel<Student>> response = new ApiResponse<>();
        try {
            Student existingStudent = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cannot update. Student not found with ID: " + id));
            existingStudent.setName(student.getName());
            existingStudent.setRollNo(student.getRollNo());
            existingStudent.setRegistrationNo(student.getRegistrationNo());
            existingStudent.setCgpa(student.getCgpa());

            EntityModel<Student> studentModel = assembler.toModel(existingStudent);
            response.setData(studentModel);
            response.setMessage("Student updated successfully!");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot delete. Student not found with ID: " + id);
        }
    }
}
