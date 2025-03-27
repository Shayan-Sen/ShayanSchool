package com.shayan.ShayanSchool.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student,String>{
    
}
