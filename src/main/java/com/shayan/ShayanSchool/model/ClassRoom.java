package com.shayan.ShayanSchool.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {
    
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = true)
    private List<String> notices;

    @Column(nullable = true)
    private List<Student> students;

    @Column(nullable = true)
    private Teacher teacher;

}
