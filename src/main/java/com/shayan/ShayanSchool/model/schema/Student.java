package com.shayan.ShayanSchool.model.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Student {

    @Id
    // @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id = generateCustomUuid();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, name = "roll_no")
    private Long rollNo;

    @Column(nullable = false, unique = true, name = "registration_no")
    private Long registrationNo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cgpa;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    @ToString.Exclude
    private ClassRoom classRoom;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    private String generateCustomUuid(){
        return "S-" + UUID.randomUUID().toString();
    }

    public Student(String name, Long rollNo, Long registrationNo, BigDecimal cgpa) {
        this.name = name;
        this.rollNo = rollNo;
        this.registrationNo = registrationNo;
        this.cgpa = cgpa;
    }
}