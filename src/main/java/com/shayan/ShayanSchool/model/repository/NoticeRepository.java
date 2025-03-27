package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    
}
