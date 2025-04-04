package com.shayan.ShayanSchool.model.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shayan.ShayanSchool.model.serializers.StudentSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = StudentSerializer.class)
public class Student {

    @Id
    // @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id = generateCustomUuid();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String rollNo;

    @Column(nullable = false, unique = true)
    private String registrationNo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cgpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    @ToString.Exclude
    private ClassRoom classRoom;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateOfEnrollment;

    private String generateCustomUuid() {
        return "S-" + UUID.randomUUID().toString();
    }

    public Student(String name, String rollNo, String registrationNo, BigDecimal cgpa) {
        this.name = name;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "Student{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", rollNo=" + rollNo + ", registrationNo="
                + registrationNo + ", cgpa=" + cgpa  + ", classRoom=" + classRoom.getName() + ", dateOfEnrollment="
                + dateOfEnrollment + '}';
    }
}