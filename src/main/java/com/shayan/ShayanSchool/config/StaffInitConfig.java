package com.shayan.ShayanSchool.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shayan.ShayanSchool.model.repository.StaffRepository;
import com.shayan.ShayanSchool.model.schema.Staff;

@Configuration
public class StaffInitConfig {
    

    @Bean
    CommandLineRunner initializeStaff(StaffRepository staffRepository) {
        return _ -> {
            if (staffRepository.count() == 0) {
                Staff defaultAdmin = new Staff();
                defaultAdmin.setStaffid("admin");
                defaultAdmin.setStaffpass("admin");
                defaultAdmin.setDesignation("principal");
                
                staffRepository.save(defaultAdmin);
            }
        };
    }
}
