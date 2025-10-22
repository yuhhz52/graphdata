package com.example.coinbe.entity;

import com.example.coinbe.entity.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    @Size(max = 100)
    @Column(length = 100)
    private String title;

    // Mỗi user thuộc 1 chapter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    // Nhiều user có thể tham gia nhiều squat
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_squats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "squat_id")
    )
    @Builder.Default
    private Set<Squat> squats = new HashSet<>();

}