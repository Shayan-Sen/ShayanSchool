package com.shayan.ShayanSchool.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.shayan.ShayanSchool.model.repository.ClassRepository;
import com.shayan.ShayanSchool.model.repository.NoticeRepository;
import com.shayan.ShayanSchool.model.repository.StudentRepository;
import com.shayan.ShayanSchool.model.repository.TeacherRepository;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

public class TeacherService {
    // View all students in classroom, view self details,view notices,change teacher
    // details of self,add and delete notices,change cgpa of student

    private ClassRepository classRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private NoticeRepository noticeRepository;

    TeacherService(ClassRepository classRepository, TeacherRepository teacherRepository,
            StudentRepository studentRepository, NoticeRepository noticeRepository) {
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public void addNotice(String className, String teacherId, Notice notice) {
        try {
            ClassRoom room = classRepository.findByName(className);
            Teacher teacher = teacherRepository.findByTeacherid(teacherId);
            if (room == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if (!teacher.getClassRooms().contains(room)) {
                throw new RuntimeException("Teacher is not assigned to this classroom");
            }
            List<Notice> notices = room.getNotices();
            notices.add(notice);
            room.setNotices(notices);
            classRepository.save(room);
            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new RuntimeException("Couldnt add notice: " + e.getMessage());
        }
    }

    public List<Student> viewAllStudentsinClass(String className, String teacherId) {
        try {
            ClassRoom room = classRepository.findByName(className);
            Teacher teacher = teacherRepository.findByTeacherid(teacherId);
            if (room == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if (!teacher.getClassRooms().contains(room)) {
                throw new RuntimeException("Teacher is not assigned to this classroom");
            }
            return room.getStudents();
        } catch (Exception e) {
            throw new RuntimeException("Unable to display students in class: " + e.getMessage());
        }
    }

    public Teacher viewTeacherDetails(String teacherid) {
        try {
            Teacher teacher = teacherRepository.findByTeacherid(teacherid);
            if (teacher == null) {
                throw new RuntimeException("Teacher not found");
            }
            return teacher;
        } catch (Exception e) {
            throw new RuntimeException("Unable to display teacher details: " + e.getMessage());
        }
    }

    public List<Notice> viewNotices(String className, String teacherId) {
        try {
            ClassRoom room = classRepository.findByName(className);
            Teacher teacher = teacherRepository.findByTeacherid(teacherId);
            if (room == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if (!teacher.getClassRooms().contains(room)) {
                throw new RuntimeException("Teacher is not assigned to this classroom");
            }
            return room.getNotices();
        } catch (Exception e) {
            throw new RuntimeException("Unable to display notices: " + e.getMessage());
        }
    }

    @Transactional
    public void changeStudentCgpa(String classname, String teacherid, String studentid, BigDecimal cgpa) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            Teacher teacher = teacherRepository.findByTeacherid(teacherid);
            Student student = studentRepository.findById(studentid)
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + studentid));
            if (classRoom == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if (!teacher.getClassRooms().contains(classRoom)) {
                throw new RuntimeException("Teacher is not allowed in this classroom");
            } else if (!classRoom.getStudents().contains(student)) {
                throw new RuntimeException("Student is not in this classroom");
            }
            student.setCgpa(cgpa);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to change student CGPA: " + e.getMessage());
        }
    }

    @Transactional
    public void changeTeacherDetails(String teacherid, Teacher teacher) {
        try {
            Teacher existingTeacher = teacherRepository.findByTeacherid(teacherid);
            if (existingTeacher == null) {
                throw new RuntimeException("Teacher not found with id " + teacherid);
            }
            existingTeacher.setTeacherid(teacher.getTeacherid());
            existingTeacher.setTeacherpass(teacher.getTeacherpass());
            teacherRepository.save(existingTeacher);

        } catch (Exception e) {
            throw new RuntimeException("Unable to change teacher details: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteNoticeById(String className, String teacherId, String noticeId) {
        try {
            ClassRoom room = classRepository.findByName(className);
            Teacher teacher = teacherRepository.findByTeacherid(teacherId);
            Notice notice = noticeRepository.findById(noticeId)
                    .orElseThrow(() -> new RuntimeException("Notice not found with id " + noticeId));
            if (room == null || teacher == null) {
                throw new RuntimeException("Classroom or Teacher not found");
            }
            if (!teacher.getClassRooms().contains(room)) {
                throw new RuntimeException("Teacher is not allowed in this classroom");
            } else if (!room.getNotices().contains(notice)) {
                throw new RuntimeException("Notice is not in this classroom");
            }
            List<Notice> notices = room.getNotices();
            notices.remove(notice);
            room.setNotices(notices);
            classRepository.save(room);
            noticeRepository.delete(notice);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete notice: " + e.getMessage());
        }
    }
}
