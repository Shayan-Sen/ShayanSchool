package com.shayan.ShayanSchool.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shayan.ShayanSchool.model.repository.NoticeRepository;
import com.shayan.ShayanSchool.model.schema.Notice;
@Service
public class NoticeService {

    private NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository repository){
        this.noticeRepository = repository;
    }

    // This cron expression runs the task every hour (at minute 0).
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredNotices() {
        LocalDateTime now = LocalDateTime.now();
        List<Notice> expiredNotices = noticeRepository.findByExpirationTimeBefore(now);
        if (!expiredNotices.isEmpty()) {
            noticeRepository.deleteAll(expiredNotices);
            // Optionally log the deletion:
            System.out.println("Deleted " + expiredNotices.size() + " expired notices at " + now);
        }
    }
}
