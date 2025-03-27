package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.schema.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,String>{
    
}
