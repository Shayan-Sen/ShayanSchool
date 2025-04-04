
package com.shayan.ShayanSchool.model.schema;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
// import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shayan.ShayanSchool.model.serializers.NoticeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = NoticeSerializer.class)
public class Notice {

    @Id
    // @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id = generateCustomUuid();

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    @ToString.Exclude
    private ClassRoom classRoom;

    @Column(nullable = true)
    private LocalDateTime expirationTime = LocalDateTime.now().plusDays(30);

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isExpired() {
        return expirationTime != null && LocalDateTime.now().isAfter(expirationTime);
    }

    public void setExpirationDuration(long hours) {
        this.expirationTime = LocalDateTime.now().plusHours(hours);
    }

    private String generateCustomUuid() {
        return "N-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Notice{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", content='" + content + '\''
                + ", classRoom=" + classRoom.getName() + ", expirationTime=" + expirationTime + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + '}';
    }
}