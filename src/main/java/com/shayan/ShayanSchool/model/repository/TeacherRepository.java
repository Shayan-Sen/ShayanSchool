package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,String>{
    
}
