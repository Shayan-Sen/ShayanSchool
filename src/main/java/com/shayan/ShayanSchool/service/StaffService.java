package com.shayan.ShayanSchool.service;

import java.util.List;

import com.shayan.ShayanSchool.model.repository.ClassRepository;
import com.shayan.ShayanSchool.model.repository.StaffRepository;
import com.shayan.ShayanSchool.model.repository.StudentRepository;
import com.shayan.ShayanSchool.model.repository.TeacherRepository;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Staff;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

import jakarta.transaction.Transactional;

public class StaffService {
    // CRUD students & teacher,CRUD staff if designation principal,CRUD notices,CRUD
    // ClassRooms
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private ClassRepository classRepository;
    private StaffRepository staffRepository;

    StaffService(StudentRepository studentRepository, TeacherRepository teacherRepository,
            ClassRepository classRepository,StaffRepository staffRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.staffRepository = staffRepository;

    }

    // STUDENTS

    @Transactional
    void addStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add student");
        }
    }

    @Transactional
    void deleteStudentById(String id) {
        try {
            Student student = studentRepository.findById(id).orElseThrow(() -> new Exception("Student not found"));
            studentRepository.delete(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete student: " + e.getMessage());
        }
    }

    // TEACHERS

    @Transactional
    void addTeacher(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add teacher");
        }
    }

    @Transactional
    void deleteTeacherById(String id) {
        try {
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new Exception("Teacher not found"));
            teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete teacher");
        }
    }

    // CLASSROOM

    @Transactional
    void addClassRoom(ClassRoom classRoom) {
        try {
            classRepository.save(classRoom);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add classRoom");
        }
    }

    @Transactional
    void addStudentToClass(String classname, String studentid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Student student = studentRepository.findById(studentid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.addStudent(student);
            classRepository.save(classRoom);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add student to classRoom: " + e.getMessage());
        }
    }

    @Transactional
    void removeStudentFromClass(String classname, String studentid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Student student = studentRepository.findById(studentid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.removeStudent(student);
            classRepository.save(classRoom);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to remove student from classRoom: " + e.getMessage());
        }
    }

    @Transactional
    void addTeacherToClass(String classname, String teacherid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Teacher teacher = teacherRepository.findById(teacherid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.addTeacher(teacher);
            classRepository.save(classRoom);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add teacher to classRoom: " + e.getMessage());
        }
    }

    @Transactional
    void removeTeacherToClass(String classname, String teacherid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Teacher teacher = teacherRepository.findById(teacherid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.removeTeacher(teacher);
            classRepository.save(classRoom);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to remove teacher from classRoom: " + e.getMessage());
        }
    }

    @Transactional
    void deleteClassRoomByName(String name) {
        try {
            ClassRoom classRoom = classRepository.findByName(name);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            List<Teacher> teachers = classRoom.getTeachers();
            List<Student> students = classRoom.getStudents();
            for (Teacher teacher1 : teachers) {
                classRoom.removeTeacher(teacher1);
                teacherRepository.save(teacher1);
            }
            for (Student student1 : students) {
                classRoom.removeStudent(student1);
                studentRepository.save(student1);
            }

            classRepository.delete(classRoom);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete classRoom: " + e.getMessage());
        }
    }

    // STAFF

    @Transactional
    void addStaff(Staff staff,String adminid) {
        try {
            Staff admin = staffRepository.findById(adminid).orElseThrow(() -> new Exception("Admin not found"));
            if (admin.getDesignation() != "principal") {
                throw new Exception("Only principal can add staff");
            }else{
                staffRepository.save(staff);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to add staff: " + e.getMessage());
        }
    }

    @Transactional
    void deleteStaffById(String id,String adminid) {
        try {
            Staff admin = staffRepository.findById(adminid).orElseThrow(() -> new Exception("Admin not found"));
            if (admin.getDesignation() != "principal") {
                throw new Exception("Only principal can delete staff");
            }else{
                staffRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete staff: " + e.getMessage());
        }
    }
}
