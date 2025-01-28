package com.shayan.ShayanTest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(updatable = false, nullable = false, columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, name = "roll_no")
    private Long rollNo;

    @Column(nullable = false, unique = true, name = "makaut_registration_no")
    private Long registrationNo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cgpa;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at", columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime createdAt;

    public Student(String name, Long rollNo, Long registrationNo, BigDecimal cgpa) {
        this.name = name;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.cgpa = cgpa;
    }
}
