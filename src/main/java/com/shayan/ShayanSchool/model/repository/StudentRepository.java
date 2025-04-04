package com.shayan.ShayanSchool.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.schema.Student;


public interface StudentRepository extends JpaRepository<Student,String>{
    Student findByRollNo(String rollno);
}
