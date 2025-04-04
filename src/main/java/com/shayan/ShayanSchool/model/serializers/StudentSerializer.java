package com.shayan.ShayanSchool.model.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shayan.ShayanSchool.model.schema.Student;

public class StudentSerializer extends JsonSerializer<Student> {
    
    @Override
    public void serialize(Student student, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", student.getId());
        gen.writeStringField("name", student.getName());
        gen.writeStringField("rollno", student.getRollNo().toString());
        gen.writeStringField("registration_no", student.getRegistrationNo().toString());
        gen.writeStringField("cgpa", student.getCgpa().toString());
        gen.writeStringField("date_of_enrollment", student.getDateOfEnrollment().toString());
        gen.writeFieldName("class");
        gen.writeStartObject();
        gen.writeStringField("id", student.getClassRoom().getId());
        gen.writeStringField("name", student.getClassRoom().getName());
        gen.writeEndObject();
        gen.writeEndObject();

    }
}
