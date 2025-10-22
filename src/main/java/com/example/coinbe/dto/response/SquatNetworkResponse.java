package com.example.coinbe.dto.response;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SquatNetworkResponse {
    private UUID squatId;
    private String squatName;
    private String squatTopic;
    private List<UserResponse> members;
    private List<EdgeResponse> edges;
}