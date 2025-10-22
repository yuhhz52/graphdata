package com.example.coinbe.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterNetworkResponse {
    private UUID chapterId;
    private String chapterName;
    private String chapterTopic;
    private List<UserResponse> members;
    private List<EdgeResponse> edges;
}