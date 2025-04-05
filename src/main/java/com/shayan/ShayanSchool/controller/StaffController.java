package com.shayan.ShayanSchool.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Staff;
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
    public ResponseEntity<ApiResponse> updateStudentCgpa(@PathVariable String id, @RequestParam BigDecimal cgpa) {
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

// --------------------------------------TEACHER-------------------------------------

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


//  ------------------------------------CLASSROOM--------------------------------------

    @PostMapping("/classroom/add")
    public ResponseEntity<ApiResponse> addClassroom(@RequestBody ClassRoom classroom) {
        try {
            staffService.addClassRoom(classroom);
            return ResponseEntity.status(OK).body(new ApiResponse("Classroom added successfully", classroom));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/classroom/{classname}/add-student/{id}")
    public ResponseEntity<ApiResponse> addStudentToClassroom(@PathVariable String classname, @PathVariable String id){
        try {
            staffService.addStudentToClass(classname, id);
            return ResponseEntity.status(OK).body(new ApiResponse("Student with id: "+id+" added to class: "+classname, null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/classroom/{classname}/delete-student/{id}")
    public ResponseEntity<ApiResponse> deleteStudentToClassroom(@PathVariable String classname, @PathVariable String id){
        try {
            staffService.removeStudentFromClass(classname, id);
            return ResponseEntity.status(OK).body(new ApiResponse("Student with id: "+id+" removed from class: "+classname, null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/classroom/{classname}/add-teacher/{id}")
    public ResponseEntity<ApiResponse> addTeacherToClassroom(@PathVariable String classname, @PathVariable String id){
        try {
            staffService.addTeacherToClass(classname, id);
            return ResponseEntity.status(OK).body(new ApiResponse("Teacher with id: "+id+" added to class: "+classname, null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/classroom/{classname}/delete-teacher/{id}")
    public ResponseEntity<ApiResponse> deleteTeacherToClassroom(@PathVariable String classname, @PathVariable String id){
        try {
            staffService.removeTeacherFromClass(classname, id);
            return ResponseEntity.status(OK).body(new ApiResponse("Teacher with id: "+id+" removed from class: "+classname, null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classrooms/all")
    public ResponseEntity<ApiResponse> getAllClassrooms(){
        try {
            List<String> classrooms = staffService.getListOfClassRooms();
            return ResponseEntity.status(OK).body(new ApiResponse("All classrooms: ", classrooms));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classroom/{classname}")
    public ResponseEntity<ApiResponse> getClassroomByName(@PathVariable String classname){
        try {
            ClassRoom classRoom = staffService.getClassRoomByName(classname);
            return ResponseEntity.status(OK).body(new ApiResponse("Classroom: "+classname, classRoom));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classroom/{classname}/notices")
    public ResponseEntity<ApiResponse> getNoticesByClassroomName(@PathVariable String classname){
        try {
            List<Notice> notices = staffService.getNoticesByClassroom(classname);
            return ResponseEntity.status(OK).body(new ApiResponse("Notices in Classroom: "+classname, notices));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/classroom/{classname}/notice/add")
    public ResponseEntity<ApiResponse> addNoticeToClassroom(@PathVariable String classname, @RequestBody Notice notice){
        try {
            staffService.addNoticeToClassroom(classname, notice);
            return ResponseEntity.status(OK).body(new ApiResponse("Notice added to classroom: "+classname, null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/classroom/{classname}/notice/{id}")
    public ResponseEntity<ApiResponse> removeNoticeFromClassroom(@PathVariable String classname, @PathVariable String id){
        try {
            staffService.deleteNoticeFromClassroom(classname, id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/classroom/{classname}")
    public ResponseEntity<ApiResponse> deleteClassroom(@PathVariable String classname){
        try {
            staffService.deleteClassRoomByName(classname);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


//  ------------------------------------------STAFF-----------------------------------------------------

    @PostMapping("/principal/staff/add")
    public ResponseEntity<ApiResponse> addStaff(@RequestBody Staff staff){
        try {
            Authentication authObject = SecurityContextHolder.getContext().getAuthentication();
            String adminid = authObject.getName();
            staffService.addStaff(staff,adminid);
            return ResponseEntity.status(OK).body(new ApiResponse("Staff added", staff));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/principal/staff/all")
    public ResponseEntity<ApiResponse> getAllStaff(){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Staff> staffs = staffService.viewAllStaff(adminid);
            return ResponseEntity.status(OK).body(new ApiResponse("All staff", staffs));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/principal/staff/id/{id}")
    public ResponseEntity<ApiResponse> getStaffById(@PathVariable String id){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            Staff staff = staffService.viewStaffById(id, adminid);
            return ResponseEntity.status(OK).body(new ApiResponse("Staff", staff));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/self")
    public ResponseEntity<ApiResponse> getSelf(){
        try {
            String selfid = SecurityContextHolder.getContext().getAuthentication().getName();
            Staff staff = staffService.viewStaffbyStaffid(selfid);
            return ResponseEntity.status(OK).body(new ApiResponse("Self", staff));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/principal/staff/staffid/{staffid}")
    public ResponseEntity<ApiResponse> getStaffByStaffId(@PathVariable String staffid){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            Staff staff = staffService.viewStaffbyStaffid(staffid,adminid);
            return ResponseEntity.status(OK).body(new ApiResponse("Self", staff));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/principal/staff/id/{id}")
    public ResponseEntity<ApiResponse> updateStaff(@PathVariable String id, @RequestBody Staff staff){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            staffService.updateStaffbyId(id,adminid,staff);
            return ResponseEntity.status(OK).body(new ApiResponse("Staff updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-self")
    public ResponseEntity<ApiResponse> updateSelf(@RequestBody Staff staff){
        try {
            String selfid = SecurityContextHolder.getContext().getAuthentication().getName();
            staffService.updateStaffbyStaffid(selfid,staff);
            return ResponseEntity.status(OK).body(new ApiResponse("Self updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/principal/staff/update/staffid/{staffid}")
    public ResponseEntity<ApiResponse> updateStaffByStaffId(@PathVariable String staffid, @RequestBody Staff staff){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            staffService.updateStaffbyStaffid(staffid,adminid,staff);
            return ResponseEntity.status(OK).body(new ApiResponse("Staff updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/principal/staff/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteStaff(@PathVariable String id){
        try {
            String adminid = SecurityContextHolder.getContext().getAuthentication().getName();
            staffService.deleteStaffById(id,adminid);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
