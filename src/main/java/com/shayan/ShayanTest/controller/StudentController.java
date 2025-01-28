package com.shayan.ShayanTest.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanTest.model.Student;
import com.shayan.ShayanTest.model.StudentRepository;

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

            response.setMessage("Succesfully extracted all student records");
            response.setData(studentModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EntityModel<Student>>> getStudentById(@PathVariable UUID id){
        ApiResponse<EntityModel<Student>> response = new ApiResponse<>();
        try {
            Student student = repository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
            EntityModel<Student> studentModel = assembler.toModel(student);

            response.setMessage("Succesful got student with id:" + id);
            response.setData(studentModel);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EntityModel<Student>>> createStudent(@RequestBody Student student){
        ApiResponse<EntityModel<Student>> response = new ApiResponse<>();
        try {
            repository.save(student);
            EntityModel<Student> studenModel = assembler.toModel(student);

            response.setMessage("Successfully created new student");
            response.setData(studenModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID id){
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id:" + id);
        }
    }
}
