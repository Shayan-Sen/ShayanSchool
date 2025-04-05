package com.shayan.ShayanSchool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.shayan.ShayanSchool.model.repository.StaffRepository;
import com.shayan.ShayanSchool.model.repository.StudentRepository;
import com.shayan.ShayanSchool.model.repository.TeacherRepository;
import com.shayan.ShayanSchool.model.schema.Staff;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private StaffRepository staffRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    SecurityConfig(StaffRepository staffRepository, TeacherRepository teacherRepository,
            StudentRepository studentRepository) {
        this.staffRepository = staffRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain staffFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/staff/**")
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/staff/principal/**").hasRole("PRINCIPAL")
                                .anyRequest().hasRole("STAFF"))
                .authenticationProvider(staffAuthProvider())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain teacherFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/teacher/**")
                .authorizeHttpRequests(
                        auth -> auth
                                .anyRequest().hasRole("TEACHER"))
                .authenticationProvider(teacherAuthenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain studentFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/student/**")
                .authorizeHttpRequests(
                        auth -> auth
                                .anyRequest().hasRole("STUDENT"))
                .authenticationProvider(studentAuthenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationProvider staffAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(staffDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationProvider teacherAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(teacherDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationProvider studentAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentDetailsService());
        return provider;
    }

    @Bean
    public UserDetailsService staffDetailsService() {
        return username -> {
            Staff staff = staffRepository.findByStaffid(username);
            if (staff != null) {
                if (staff.getDesignation().equals("principal")) {
                    return User.builder().username(staff.getStaffid()).password("{noop}" + staff.getStaffpass())
                            .roles("PRINCIPAL", "STAFF").build();
                } else {
                    return User.builder().username(staff.getStaffid()).password("{noop}" + staff.getStaffpass())
                            .roles("STAFF")
                            .build();
                }
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public UserDetailsService teacherDetailsService() {
        return username -> {
            Teacher teacher = teacherRepository.findByTeacherid(username);
            if (teacher != null) {
                return User.builder().username(teacher.getTeacherid()).password("{noop}" + teacher.getTeacherpass())
                        .roles("TEACHER").build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public UserDetailsService studentDetailsService() {
        return username -> {
            Student student = studentRepository.findByRollNo(username);
            if (student != null) {
                return User.builder().username(student.getRollNo()).password("{noop}" + student.getRegistrationNo())
                        .roles("STUDENT").build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }
}
