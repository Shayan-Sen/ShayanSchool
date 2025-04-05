package com.shayan.ShayanSchool.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;
import com.shayan.ShayanSchool.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService service;

    StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Staff Service is up and running";
    }

    @GetMapping("/self")
    public ResponseEntity<ApiResponse> viewSelfDetails(){
        try {
            String rollno = SecurityContextHolder.getContext().getAuthentication().getName();
            Student student = service.viewStudentDetails(rollno);
            return ResponseEntity.ok(new ApiResponse("Student Details Retrieved Successfully", student));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classroom/{classname}/students")
    public ResponseEntity<ApiResponse> viewClassroomStudents(@PathVariable String classname){
        try {
            String rollno = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Student> students = service.viewStudents(classname,rollno);
            return ResponseEntity.ok(new ApiResponse("Students Retrieved Successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classroom/{classname}/teachers")
    public ResponseEntity<ApiResponse> viewClassroomTeachers(@PathVariable String classname){
        try {
            String rollno = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Teacher> teachers = service.viewTeachers(classname,rollno);
            return ResponseEntity.ok(new ApiResponse("Teachers Retrieved Successfully", teachers));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/classroom/{classname}/notices")
    public ResponseEntity<ApiResponse> viewClassroomNotices(@PathVariable String classname){
        try {
            String rollno = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Notice> notices = service.viewNotices(classname,rollno);
            return ResponseEntity.ok(new ApiResponse("Notices Retrieved Successfully", notices));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
