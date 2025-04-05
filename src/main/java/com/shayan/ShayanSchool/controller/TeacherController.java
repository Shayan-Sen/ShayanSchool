package com.shayan.ShayanSchool.controller;

import static org.springframework.http.HttpStatus.*;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;
import com.shayan.ShayanSchool.service.TeacherService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private TeacherService service;

    TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Staff Service is up and running";
    }

    @PostMapping("/classroom/{classname}/add-notice")
    public ResponseEntity<ApiResponse> addNotice(@PathVariable String classname, @RequestBody Notice notice) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            service.addNotice(classname,teacherid,notice);
            return ResponseEntity.ok(new ApiResponse( "Notice Added Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/classroom/{classname}/students")
    public ResponseEntity<ApiResponse> viewAllStudents(@PathVariable String classname) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Student> students = service.viewAllStudentsinClass(classname,teacherid);
            return ResponseEntity.ok(new ApiResponse( "All Students in class: " + classname,students));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/self")
    public ResponseEntity<ApiResponse> viewTeacherInfo() {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            Teacher teacher = service.viewTeacherDetails(teacherid);
            return ResponseEntity.ok(new ApiResponse( "Teacher Info",teacher));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/classroom/{classname}/notices")
    public ResponseEntity<ApiResponse> viewAllNotices(@PathVariable String classname) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Notice> notices = service.viewNotices(classname,teacherid);
            return ResponseEntity.ok(new ApiResponse( "All Notices in class: " + classname,notices));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/classroom/{classname}/cgpa/{id}")
    public ResponseEntity<ApiResponse> updateCGPA(@PathVariable String classname, @PathVariable String id,@RequestParam BigDecimal cgpa) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            service.changeStudentCgpa(classname, teacherid, id, cgpa);
            return ResponseEntity.ok(new ApiResponse( "CGPA Updated Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update-self")
    public ResponseEntity<ApiResponse> changeTeacherInfo(@RequestBody Teacher teacher) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            service.changeTeacherDetails(teacherid,teacher);
            return ResponseEntity.ok(new ApiResponse( "Teacher Info Updated Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/classroom/{classname}/delete-notice/{id}")
    public ResponseEntity<ApiResponse> deleteNotice(@PathVariable String classname, @PathVariable String id) {
        try {
            String teacherid = SecurityContextHolder.getContext().getAuthentication().getName();
            service.deleteNoticeById(classname, teacherid, id);;
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
