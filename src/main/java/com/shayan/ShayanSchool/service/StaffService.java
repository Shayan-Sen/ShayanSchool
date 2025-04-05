package com.shayan.ShayanSchool.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

@Service
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
    public void addStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add student: " + e.getMessage());
        }
    }

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all students: " + e.getMessage());
        }
    }

    public List<Student> getStudentsByClass(String classname) {
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

    public Student getStudentById(String id) {
        try {
            return studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student doesn't exist with id " + id));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get student by id: " + e.getMessage());
        }
    }

    @Transactional
    public Student updateStudentDetails(Student updatedStudent, String id) {
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
    public Student updateStudentCgpa(BigDecimal cgpa, String id) {
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
    public void deleteStudentById(String id) {
        try {
            Student student = studentRepository.findById(id).orElseThrow(() -> new Exception("Student not found"));
            studentRepository.delete(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete student: " + e.getMessage());
        }
    }

    // TEACHERS

    @Transactional
    public void addTeacher(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add teacher: " + e.getMessage());
        }
    }

    public List<Teacher> getAllTeacher() {
        try {
            return teacherRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get all teachers: " + e.getMessage());
        }
    }

    public List<Teacher> getTeacherByClass(String classname) {
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

    public Teacher getTeacherById(String id) {
        try {
            return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to get teacher by id: " + e.getMessage());
        }
    }

    @Transactional
    public Teacher updateTeacherDetails(Teacher teacher, String id) {
        try {
            Teacher existingTeacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            existingTeacher.setTeacherid(teacher.getTeacherid());
            existingTeacher.setTeacherpass(teacher.getTeacherpass());
            // existingTeacher.setClassRooms(teacher.getClassRooms());
            return teacherRepository.save(existingTeacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update teacher by id: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteTeacherById(String id) {
        try {
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new Exception("Teacher not found"));
            teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete teacher: " + e.getMessage());
        }
    }

    // CLASSROOM

    @Transactional
    public void addClassRoom(ClassRoom classRoom) {
        try {
            classRepository.save(classRoom);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add classRoom");
        }
    }

    @Transactional
    public void addStudentToClass(String classname, String studentid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Student student = studentRepository.findById(studentid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.addStudent(student);
            student.setClassRoom(classRoom);
            classRepository.save(classRoom);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add student to classRoom: " + e.getMessage());
        }
    }

    @Transactional
    public void removeStudentFromClass(String classname, String studentid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Student student = studentRepository.findById(studentid)
                    .orElseThrow(() -> new Exception("Student not found"));
            classRoom.removeStudent(student);
            student.setClassRoom(null);
            classRepository.save(classRoom);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Unable to remove student from classRoom: " + e.getMessage());
        }
    }

    @Transactional
    public void addTeacherToClass(String classname, String teacherid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Teacher teacher = teacherRepository.findById(teacherid)
                    .orElseThrow(() -> new Exception("Teacher not found"));
            classRoom.addTeacher(teacher);
            List<ClassRoom> classRooms = teacher.getClassRooms();
            classRooms.add(classRoom);
            teacher.setClassRooms(classRooms);
            classRepository.save(classRoom);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to add teacher to classRoom: " + e.getMessage());
        }
    }

    @Transactional
    public void removeTeacherFromClass(String classname, String teacherid) {
        try {
            ClassRoom classRoom = classRepository.findByName(classname);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            Teacher teacher = teacherRepository.findById(teacherid)
                    .orElseThrow(() -> new Exception("Teacher not found"));
            classRoom.removeTeacher(teacher);
            List<ClassRoom> classRooms = teacher.getClassRooms();
            classRooms.remove(classRoom);
            teacher.setClassRooms(classRooms);
            classRepository.save(classRoom);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Unable to remove teacher from classRoom: " + e.getMessage());
        }
    }

    public List<String> getListOfClassRooms() {
        try {
            return classRepository.findAll().stream().map(classRoom -> classRoom.getName())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get list of classRooms: " + e.getMessage());
        }
    }

    public ClassRoom getClassRoomByName(String name) {
        try {
            return classRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get classRoom by name: " + e.getMessage());
        }
    }

    public List<Notice> getNoticesByClassroom(String name) {
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
    public void addNoticeToClassroom(String name, Notice notice) {
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
        } catch (Exception e) {
            throw new RuntimeException("Unable to add notice to classRoom: " + e.getMessage());
        }

    }

    @Transactional
    public void deleteNoticeFromClassroom(String name, String noticeId) {
        try {
            ClassRoom classRoom = classRepository.findByName(name);
            Notice notice = noticeRepository.findById(noticeId)
                    .orElseThrow(() -> new RuntimeException("Notice Not found"));
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            List<Notice> notices = classRoom.getNotices();
            notices.remove(notice);
            classRoom.setNotices(notices);
            classRepository.save(classRoom);
            noticeRepository.deleteById(noticeId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete notice from classRoom: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteClassRoomByName(String name) {
        try {
            ClassRoom classRoom = classRepository.findByName(name);
            if (classRoom == null) {
                throw new Exception("ClassRoom not found");
            }
            List<Teacher> teachers = new ArrayList<>(classRoom.getTeachers());
            List<Student> students = new ArrayList<>(classRoom.getStudents());
            for (Teacher teacher1 : teachers) {
                classRoom.removeTeacher(teacher1);
                List<ClassRoom> classRooms = teacher1.getClassRooms();
                classRooms.remove(classRoom);
                teacher1.setClassRooms(classRooms);
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
    public void addStaff(Staff staff, String adminid) {
        try {
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null||!admin.getDesignation().equals("principal")) {
                throw new Exception("Only principal can add staff");
            } else {
                staffRepository.save(staff);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to add staff: " + e.getMessage());
        }
    }

    public List<Staff> viewAllStaff(String adminid){
        try {
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null||!admin.getDesignation().equals("principal")) {
                throw new Exception("Only principal can view staff");
            }
            return staffRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unable to view staff: " + e.getMessage());
        }
    }

    public Staff viewStaffById(String id,String adminid){
        try {
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null||!admin.getDesignation().equals("principal")) {
                throw new Exception("Only principal can view staff");
            }
            return staffRepository.findById(id).orElseThrow(()-> new RuntimeException("Staff not found with id " + id));
        } catch (Exception e) {
            throw new RuntimeException("Unable to view staff: " + e.getMessage());
        }
    }

    public Staff viewStaffbyStaffid(String staffid){
        try {
            Staff staff = staffRepository.findByStaffid(staffid);
            if (staff == null) {
                throw new Exception("Staff not found with staffid " + staffid);
            }
            return staff;
        } catch (Exception e) {
            throw new RuntimeException("Unable to view staff: " + e.getMessage());
        }
    }
    public Staff viewStaffbyStaffid(String staffid,String adminid){
        try {
            Staff staff = staffRepository.findByStaffid(staffid);
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null || staff == null || !admin.getDesignation().equals("principal")) {
                throw new Exception("Staff not found with staffid " + staffid);
            }
            return staff;
        } catch (Exception e) {
            throw new RuntimeException("Unable to view staff: " + e.getMessage());
        }
    }

    @Transactional
    public void updateStaffbyId(String id,String adminid,Staff staff){
        try {
            Staff admin = staffRepository.findByStaffid(adminid);
            Staff existingStaff = staffRepository.findById(id).orElseThrow(()-> new RuntimeException("Staff not found with id " + id));
            if (admin == null||!admin.getDesignation().equals("principal")) {
                throw new Exception("Only principal can view staff");
            }
            existingStaff.setStaffid(staff.getStaffid());
            existingStaff.setStaffpass(staff.getStaffpass());

            staffRepository.save(existingStaff);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update staff: " + e.getMessage());
        }
    }

    @Transactional
    public void updateStaffbyStaffid(String staffid,Staff staff){
        try {
            Staff existingStaff = staffRepository.findByStaffid(staffid);
            if (existingStaff == null) {
                throw new Exception("Staff not found with staffid " + staffid);
            }
            existingStaff.setStaffid(staff.getStaffid());
            existingStaff.setStaffpass(staff.getStaffpass());
            staffRepository.save(existingStaff);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update staff: " + e.getMessage());
        }
    }

    @Transactional
    public void updateStaffbyStaffid(String staffid,String adminid,Staff staff){
        try {
            Staff existingStaff = staffRepository.findByStaffid(staffid);
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null || existingStaff == null || !admin.getDesignation().equals("principal")) {
                throw new Exception("Staff not found with staffid " + staffid);
            }
            existingStaff.setStaffid(staff.getStaffid());
            existingStaff.setStaffpass(staff.getStaffpass());

            staffRepository.save(existingStaff);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update staff: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteStaffById(String id, String adminid) {
        try {
            Staff admin = staffRepository.findByStaffid(adminid);
            if (admin == null ||!admin.getDesignation().equals("principal")) {
                throw new Exception("Only principal can delete staff");
            } else {
                staffRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete staff: " + e.getMessage());
        }
    }
}
