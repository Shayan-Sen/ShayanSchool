package com.shayan.ShayanSchool.service;

import java.util.List;

import com.shayan.ShayanSchool.model.repository.ClassRepository;
import com.shayan.ShayanSchool.model.repository.TeacherRepository;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

public class TeacherService {
    // View all students in classroom, view self details,view notices,change teacher
    // details of self,add and delete notices,change cgpa of student

    private ClassRepository classRepository;
    private TeacherRepository teacherRepository;

    TeacherService(ClassRepository classRepository, TeacherRepository teacherRepository) {
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Student> viewAllStudentsinClass(String className, String teacherId) {
        try {
            ClassRoom room = classRepository.findByName(className);
            Teacher teacher = teacherRepository.findByTeacherid(teacherId);
            if (room == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if(!teacher.getClassRooms().contains(room)){
                throw new RuntimeException("Teacher is not assigned to this classroom");
            }
            return room.getStudents();
        } catch (Exception e) {
            throw new RuntimeException("Unable to display students in class: " + e.getMessage());
        }
    }

    public Teacher viewTeacherDetails(String teacherid) {
        try {
            return teacherRepository.findByTeacherid(teacherid);
        } catch (Exception e) {
            throw new RuntimeException("Unable to display teacher details: " + e.getMessage());
        }
    }
}
