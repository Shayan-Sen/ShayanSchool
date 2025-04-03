package com.shayan.ShayanSchool.model.schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UuidGenerator;

// import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher {
    
    @Id
    // @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id = generateCustomUuid();

    @Column(nullable = false,unique = true)
    private String teacherid;

    @Column(nullable = false)
    private String teacherpass;

    @ManyToMany(mappedBy = "teachers") //, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<ClassRoom> classRooms = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime joiningDate;

    // public void addClassRoom(ClassRoom classRoom) {
    //     if (!classRooms.contains(classRoom)) {
    //         classRooms.add(classRoom);
    //         classRoom.getTeachers().add(this);
    //     }
    // }

    // public void removeClassRoom(ClassRoom classRoom) {
    //     classRooms.remove(classRoom);
    //     classRoom.getTeachers().remove(this);
    // }

    private String generateCustomUuid(){
        return "T-" + UUID.randomUUID().toString();
    }
}