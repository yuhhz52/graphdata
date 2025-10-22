package com.example.coinbe.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeResponse {
    private UUID id;
    private String type; //  "CHAPTER", "CLAN", "SQUAT"
    private String label; // tên hiển thị
    private String description; // thêm mô tả nếu có
}