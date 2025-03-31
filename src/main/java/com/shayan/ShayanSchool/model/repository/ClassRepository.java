package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.schema.ClassRoom;


public interface ClassRepository extends JpaRepository<ClassRoom,String>{
    ClassRoom findByName(String name);
}
