package com.example.coinbe.entity;

import com.example.coinbe.entity.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "squats",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Squat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Squat name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 200)
    @Column(length = 200)
    private String topic;

    // Nhiều squat có thể chứa nhiều user (geeks)
    @ManyToMany(mappedBy = "squats")
    @Builder.Default
    private Set<User> geeks = new HashSet<>();


}