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
import com.shayan.ShayanSchool.model.schema.Teacher;
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

    @PostMapping("/student/add")
    public ResponseEntity<ApiResponse> addStudents(@RequestBody Student student) {
        try {
            staffService.addStudent(student);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Succesfully added student", student));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/students/all")
    public ResponseEntity<ApiResponse> viewAllStudents() {
        try {
            List<Student> students = staffService.getAllStudents();
            return ResponseEntity.status(OK)
                    .body(new ApiResponse("Succesfully retrieved all students in school", students));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/students/class/{classname}")
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
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/student/cgpa/{id}")
    public ResponseEntity<ApiResponse> updateStudentCgpa(@PathVariable String id, @RequestBody BigDecimal cgpa) {
        try {
            Student student = staffService.updateStudentCgpa(cgpa, id);
            return ResponseEntity.status(ACCEPTED)
                    .body(new ApiResponse("Succesfully updated student cgpa with id: " + id, student));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
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

    // --------------------------------TEACHER-------------------------------------

    @PostMapping("/teacher/add")
    public ResponseEntity<ApiResponse> addTeacher(@RequestBody Teacher teacher) {
        try {
            staffService.addTeacher(teacher);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Teacher added successfully", teacher));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/teachers/all")
    public ResponseEntity<ApiResponse> getAllTeachers() {
        try {
            List<Teacher> teachers = staffService.getAllTeacher();
            return ResponseEntity.ok().body(new ApiResponse("Successfully retrieved all teachers", teachers));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/teachers/class/{classname}")
    public ResponseEntity<ApiResponse> getTeacherByClass(@PathVariable String classname) {
        try {
            List<Teacher> teachers = staffService.getTeacherByClass(classname);
            return ResponseEntity.status(OK)
                    .body(new ApiResponse("Successfully retrieved teachers for class: " + classname, teachers));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<ApiResponse> getTeacherById(@PathVariable String id) {
        try {
            Teacher teacher = staffService.getTeacherById(id);
            return ResponseEntity.ok().body(new ApiResponse("Successfully retrieved teacher with id: " + id, teacher));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/teacher/{id}")
    public ResponseEntity<ApiResponse> updateTeacher(@PathVariable String id, @RequestBody Teacher teacher) {
        try {
            Teacher updatedTeacher = staffService.updateTeacherDetails(teacher,id);
            return ResponseEntity.status(OK).body(new ApiResponse("Teacher updated successfully", updatedTeacher));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<ApiResponse> deleteTeacher(@PathVariable String id) {
        try {
            staffService.deleteTeacherById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
