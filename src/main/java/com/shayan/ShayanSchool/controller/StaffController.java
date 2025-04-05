package com.shayan.ShayanSchool.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.service.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Staff Service is up and running";
    }

// ----------------------------------STUDENT---------------------------------

    @PostMapping("/add-students")
    public ResponseEntity<ApiResponse> addStudents(@RequestBody Student student) {
        try {
            staffService.addStudent(student);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Succesfully added student", student));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse("Failed to add student", e.getMessage()));
        }
    }

    @GetMapping("/view-all-students")
    public ResponseEntity<ApiResponse> viewAllStudents() {
        try {
            List<Student> students = staffService.getAllStudents();
            return ResponseEntity.status(OK)
                    .body(new ApiResponse("Succesfully retrieved all students in school", students));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/class/{classname}/all-students")
    public ResponseEntity<ApiResponse> viewAllStudentsByClass(@PathVariable String classname) {
        try {
            List<Student> students = staffService.getStudentsByClass(classname);
            return ResponseEntity.status(OK).body(new ApiResponse("Retrieved all students of " + classname, students));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<ApiResponse> viewStudentById(@PathVariable String id) {
        try {
            Student student = staffService.getStudentById(id);
            return ResponseEntity.status(OK).body(new ApiResponse("Retrieved student with id " + id, student));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable String id, @RequestBody Student updateStudent) {
        try {
            Student student = staffService.updateStudentDetails(updateStudent, id);
            return ResponseEntity.status(ACCEPTED)
                    .body(new ApiResponse("Succesfully updated student with id: " + id, student));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse("Failed to update student", e.getMessage()));
        }
    }

    @PutMapping("/student/cgpa/{id}")
    public ResponseEntity<ApiResponse> updateStudentCgpa(@PathVariable String id, @RequestBody BigDecimal cgpa) {
        try {
            Student student = staffService.updateStudentCgpa(cgpa, id);
            return ResponseEntity.status(ACCEPTED)
                    .body(new ApiResponse("Succesfully updated student cgpa with id: " + id, student));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse("Failed to update student", e.getMessage()));
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable String id) {
        try {
            staffService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


//  --------------------------------TEACHER-------------------------------------
}
