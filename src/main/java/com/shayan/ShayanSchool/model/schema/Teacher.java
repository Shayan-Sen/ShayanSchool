package com.shayan.ShayanSchool.model.schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shayan.ShayanSchool.model.serializers.TeacherSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonSerialize(using = TeacherSerializer.class)
public class Teacher {

    @Id
    private String id = generateCustomUuid();

    @Column(nullable = false, unique = true)
    private String teacherid;

    @Column(nullable = false)
    private String teacherpass;

    @ManyToMany(mappedBy = "teachers",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ClassRoom> classRooms = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime joiningDate;

    private String generateCustomUuid() {
        return "T-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", teacherid=" + teacherid + ", teacherpass=" + teacherpass + ", joiningDate="
                + joiningDate + "]";
    }
}