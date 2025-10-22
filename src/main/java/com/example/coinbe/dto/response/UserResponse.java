package com.example.coinbe.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL) // các trường null sẽ bị ẩn
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String title;

}