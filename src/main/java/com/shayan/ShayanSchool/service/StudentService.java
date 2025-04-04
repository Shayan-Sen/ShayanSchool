package com.shayan.ShayanSchool.service;

import java.util.List;

import com.shayan.ShayanSchool.model.repository.ClassRepository;
import com.shayan.ShayanSchool.model.repository.StudentRepository;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

public class StudentService {
    //View all students in classroom, view self details,view notices,view teacher
    private StudentRepository studentRepository;
    private ClassRepository classRepository;

    StudentService(StudentRepository studentRepository,ClassRepository classRepository){
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    Student viewStudentDetails(String rollno){
        try {
            Student student =  studentRepository.findByRollNo(rollno);
            if (student == null) {
                throw new RuntimeException("Student not found");
            }
            return student;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching student details: " + e.getMessage());
        }
    }


    List<Student> viewStudents(String className,String rollno){
        try {
            Student student = studentRepository.findByRollNo(rollno);
            ClassRoom classRoom = classRepository.findByName(className);
            if(classRoom == null || student == null){
                throw new RuntimeException("Invalid class name or roll number");
            }
            if (!student.getClassRoom().equals(classRoom)) {
                throw new RuntimeException("Student is not in this class");
            }
            return classRoom.getStudents();

        } catch (Exception e) {
            throw new RuntimeException("Unable to view all students in class: " + e.getMessage());
        }
    }


    List<Teacher> viewTeachers(String className,String rollno){
        try {
            Student student = studentRepository.findByRollNo(rollno);
            ClassRoom classRoom = classRepository.findByName(className);
            if(classRoom == null || student == null){
                throw new RuntimeException("Invalid class name or roll number");
            }
            if (!student.getClassRoom().equals(classRoom)) {
                throw new RuntimeException("Student is not in this class");
            }
            return classRoom.getTeachers();

        } catch (Exception e) {
            throw new RuntimeException("Unable to view all teachers in class: " + e.getMessage());
        }
    }

    List<Notice> viewNotices(String className,String rollno){
        try {
            Student student = studentRepository.findByRollNo(rollno);
            ClassRoom classRoom = classRepository.findByName(className);
            if(classRoom == null || student == null){
                throw new RuntimeException("Invalid class name or roll number");
            }
            if (!student.getClassRoom().equals(classRoom)) {
                throw new RuntimeException("Student is not in this class");
            }
            return classRoom.getNotices();

        } catch (Exception e) {
            throw new RuntimeException("Unable to view all notices in class: " + e.getMessage());
        }
    }
}
