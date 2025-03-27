package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.schema.Staff;

public interface StaffRepository extends JpaRepository<Staff,String>{
    
}
