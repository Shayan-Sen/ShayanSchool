package com.shayan.ShayanSchool.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.shayan.ShayanSchool.model.Student;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class StudentAssembler implements RepresentationModelAssembler<Student,EntityModel<Student>>{
    
    @Override
    @NonNull
    public EntityModel<Student> toModel(@NonNull Student student){
        return EntityModel.of(student,
        linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
        linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")
        );
    }
}
