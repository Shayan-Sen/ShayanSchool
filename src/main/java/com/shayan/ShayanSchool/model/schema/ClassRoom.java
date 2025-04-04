package com.shayan.ShayanSchool.model.schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shayan.ShayanSchool.model.serializers.ClassRoomSerializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ClassRoomSerializer.class)
public class ClassRoom {

    @Id
    private String id = generateCustomUuid();


    @Column(nullable = false, unique = true)
    private String name;


    @ManyToMany
    @JoinTable(name = "classroom_teacher", joinColumns = @JoinColumn(name = "classroom_id"),
    inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @ToString.Exclude
    private List<Teacher> teachers = new ArrayList<>();


    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<Notice> notices = new ArrayList<>();


    @OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Student> students = new ArrayList<>();


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.getClassRooms().add(this);
        }
    }


    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            student.setClassRoom(this);
        }
    }


    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.getClassRooms().remove(this);
    }


    public void removeStudent(Student student) {
        students.remove(student);
        student.setClassRoom(null);
    }


    private String generateCustomUuid() {
        return "C-" + UUID.randomUUID().toString();
    }


    @Override
    public String toString() {
        return "ClassRoom{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", createdAt=" + createdAt + '}';
    }
}