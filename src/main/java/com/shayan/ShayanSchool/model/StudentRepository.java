package com.shayan.ShayanSchool.model;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String>{
    
}
