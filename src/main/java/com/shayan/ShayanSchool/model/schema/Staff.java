package com.shayan.ShayanSchool.model.schema;

import java.util.UUID;

// import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Staff {

    @Id
    // @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id = generateCustomUuid();

    @Column(nullable = false,unique = true)
    private String staffid;

    @Column(nullable = false)
    private String staffpass;

    @Column(nullable = false)
    private String designation;

    private String generateCustomUuid(){
        return "A-" + UUID.randomUUID().toString();
    }
}
