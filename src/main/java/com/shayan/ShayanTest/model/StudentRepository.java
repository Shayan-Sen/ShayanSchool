package com.shayan.ShayanTest.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,UUID>{
    
}
