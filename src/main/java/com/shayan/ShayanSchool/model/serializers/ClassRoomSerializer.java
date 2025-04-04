package com.shayan.ShayanSchool.model.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shayan.ShayanSchool.model.schema.ClassRoom;
import com.shayan.ShayanSchool.model.schema.Notice;
import com.shayan.ShayanSchool.model.schema.Student;
import com.shayan.ShayanSchool.model.schema.Teacher;

public class ClassRoomSerializer extends JsonSerializer<ClassRoom>{
    
    @Override
    public void serialize(ClassRoom classRoom,JsonGenerator gen,SerializerProvider serializer)throws IOException{
        gen.writeStartObject();
        gen.writeStringField("id", classRoom.getId());
        gen.writeStringField("name", classRoom.getName());
        gen.writeStringField("created_at",classRoom.getCreatedAt().toString());
        
        gen.writeArrayFieldStart("teachers");
        for(Teacher teacher: classRoom.getTeachers()){
            gen.writeStartObject();
            gen.writeStringField("id", teacher.getId());
            gen.writeStringField("teacherid", teacher.getTeacherid());
            gen.writeEndObject();
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart("students");
        for(Student student: classRoom.getStudents()){
            gen.writeStartObject();
            gen.writeStringField("id", student.getId());
            gen.writeStringField("rollno", student.getRollNo().toString());
            gen.writeEndObject();
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart("notices");
        for(Notice notice: classRoom.getNotices()){
            gen.writeStartObject();
            gen.writeStringField("id", notice.getId());
            gen.writeStringField("title", notice.getTitle());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
