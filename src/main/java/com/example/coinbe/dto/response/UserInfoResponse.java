package com.example.coinbe.dto.response;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserInfoResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String title;

}