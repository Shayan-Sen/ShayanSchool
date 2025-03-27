package com.shayan.ShayanSchool.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, name = "roll_no")
    private Long rollNo;

    @Column(nullable = false, unique = true, name = "makaut_registration_no")
    private Long registrationNo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cgpa;

    @Column(nullable = true)
    private ClassRoom classRoom;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    public Student(String name, Long rollNo, Long registrationNo, BigDecimal cgpa) {
        this.name = name;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.cgpa = cgpa;
    }
}
