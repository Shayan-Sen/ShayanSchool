package com.shayan.ShayanSchool.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.shayan.ShayanSchool.model.repository.ClassRepository;
import com.shayan.ShayanSchool.model.repository.NoticeRepository;
import com.shayan.ShayanSchool.model.repository.StaffRepository;
import com.shayan.ShayanSchool.model.repository.StudentRepository;
import com.shayan.ShayanSchool.model.repository.TeacherRepository;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Notice;
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
    private NoticeRepository noticeRepository;

    StaffService(StudentRepository studentRepository, TeacherRepository teacherRepository,
            ClassRepository classRepository, StaffRepository staffRepository, NoticeRepository noticeRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.staffRepository = staffRepository;
        this.noticeRepository = noticeRepository;

    }

    // STUDENTS

    @Transactional
    void addStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add student: " + e.getMessage());
        }
    }

    List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all students: " + e.getMessage());
        }
    }

    List<Student> getStudentsByClass(String classname) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            return classRoom.getStudents();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get students by class: " + e.getMessage());
        }
    }

    Student getStudentById(String id) {
        try {
            return studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student doesn't exist with id " + id));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get student by id: " + e.getMessage());
        }
    }

    @Transactional
    Student updateStudentDetails(Student updatedStudent, String id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student doesn't exist with id " + id));
            student.setName(updatedStudent.getName());
            // student.setClassRoom(updatedStudent.getClassRoom());
            student.setRollNo(updatedStudent.getRollNo());
            student.setRegistrationNo(updatedStudent.getRegistrationNo());
            student.setCgpa(updatedStudent.getCgpa());

            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update student by id: " + e.getMessage());
        }
    }

    @Transactional
    Student updateStudentCgpa(BigDecimal cgpa, String id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student doesn't exist with id " + id));
            student.setCgpa(cgpa);

            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update student by id: " + e.getMessage());
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
            throw new RuntimeException("Unable to add teacher: " + e.getMessage());
        }
    }

    List<Teacher> getAllTeacher() {
        try {
            return teacherRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all teachers: " + e.getMessage());
        }
    }

    List<Teacher> getTeacherByClass(String classname) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            return classRoom.getTeachers();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get teacher by class name: " + e.getMessage());
        }
    }

    Teacher getTeacherById(String id) {
        try {
            return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get teacher by id: " + e.getMessage());
        }
    }

    @Transactional
    Teacher updateTeacherDetails(Teacher teacher, String id) {
        try {
            Teacher existingTeacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            existingTeacher.setTeachername(teacher.getTeachername());
            existingTeacher.setTeacherpass(teacher.getTeacherpass());
            // existingTeacher.setClassRooms(teacher.getClassRooms());
            return teacherRepository.save(existingTeacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update teacher by id: " + e.getMessage());
        }
    }

    @Transactional
    void deleteTeacherById(String id) {
        try {
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new Exception("Teacher not found"));
            teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete teacher: " + e.getMessage());
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

    List<String> getListOfClassRooms() {
        try {
            return classRepository.findAll().stream().map(classRoom -> classRoom.getName())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get list of classRooms: " + e.getMessage());
        }
    }

    ClassRoom getClassRoomByName(String name) {
        try {
            return classRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get classRoom by name: " + e.getMessage());
        }
    }

    List<Notice> getNoticesByClassroom(String name) {
        try {
            ClassRoom classRoom = classRepository.findByName(name);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            return classRoom.getNotices();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get notices by classRoom: " + e.getMessage());
        }
    }

    @Transactional
    void addNoticeToClassroom(String name, Notice notice) {
        try {
            ClassRoom classRoom = classRepository.findByName(name);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
                }
            List<Notice> notices = classRoom.getNotices();
            notices.add(notice);
            classRoom.setNotices(notices);
            classRepository.save(classRoom);
            noticeRepository.save(notice);
        }catch(Exception e){
            throw new RuntimeException("Unable to add notice to classRoom: " + e.getMessage());
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
    void removeTeacherFromClass(String classname, String teacherid) {
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
    void addStaff(Staff staff, String adminid) {
        try {
            Staff admin = staffRepository.findById(adminid).orElseThrow(() -> new Exception("Admin not found"));
            if (admin.getDesignation() != "principal") {
                throw new Exception("Only principal can add staff");
            } else {
                staffRepository.save(staff);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to add staff: " + e.getMessage());
        }
    }

    @Transactional
    void deleteStaffById(String id, String adminid) {
        try {
            Staff admin = staffRepository.findById(adminid).orElseThrow(() -> new Exception("Admin not found"));
            if (admin.getDesignation() != "principal") {
                throw new Exception("Only principal can delete staff");
            } else {
                staffRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete staff: " + e.getMessage());
        }
    }
}
