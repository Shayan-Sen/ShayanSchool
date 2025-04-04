package com.shayan.ShayanSchool.model.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Teacher;

public class TeacherSerializer extends JsonSerializer<Teacher>{
    
    @Override
    public void serialize(Teacher teacher, JsonGenerator gen, SerializerProvider serializer) throws IOException{
        gen.writeStartObject();
        gen.writeStringField("id", teacher.getId());
        gen.writeStringField("teacherid", teacher.getTeacherid());
        gen.writeStringField("teacherpass", teacher.getTeacherpass());
        gen.writeStringField("joining_date", teacher.getJoiningDate().toString());
        
        gen.writeArrayFieldStart("classrooms");
        for (ClassRoom classroom : teacher.getClassRooms()) {
            gen.writeStartObject();
            gen.writeStringField("id", classroom.getId());
            gen.writeStringField("name", classroom.getName());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
