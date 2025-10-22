package com.example.coinbe.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdgeResponse {
    private UUID source; // ID node bắt đầu
    private UUID target; // ID node kết thúc
    private String relation; // "BELONGS_TO", "MEMBER_OF"
}