package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.entity.ClassRoom;

public interface ClassRepository extends JpaRepository<ClassRoom,String>{
    
}
