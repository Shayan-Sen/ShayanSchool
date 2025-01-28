package com.shayan.ShayanTest.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.shayan.ShayanTest.model.Student;

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
