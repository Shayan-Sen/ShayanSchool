package com.shayan.ShayanSchool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayan.ShayanSchool.model.schema.Notice;
import java.util.List;
import java.time.LocalDateTime;


public interface NoticeRepository extends JpaRepository<Notice, String> {
    List<Notice> findByExpirationTimeBefore(LocalDateTime expirationTime);
}
