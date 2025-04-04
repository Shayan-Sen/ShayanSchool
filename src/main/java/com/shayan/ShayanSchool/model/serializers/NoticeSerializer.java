package com.shayan.ShayanSchool.model.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shayan.ShayanSchool.model.schema.Notice;

public class NoticeSerializer extends JsonSerializer<Notice>{
    @Override
    public void serialize(Notice notice, JsonGenerator gen, SerializerProvider serializer) throws IOException{
        gen.writeStartObject();
        gen.writeStringField("id", notice.getId());
        gen.writeStringField("title", notice.getTitle());
        gen.writeStringField("content", notice.getContent());
        gen.writeStringField("created_at", notice.getCreatedAt().toString());
        gen.writeStringField("updated_at", notice.getUpdatedAt().toString());
        gen.writeStringField("expiration_time", notice.getExpirationTime().toString());
        gen.writeFieldName("classroom");
        gen.writeStartObject();
        gen.writeStringField("id", notice.getClassRoom().getId());
        gen.writeStringField("name", notice.getClassRoom().getName());
        gen.writeEndObject();
        gen.writeEndObject();
    }
}
