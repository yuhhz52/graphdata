package com.example.coinbe.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkResponse {

    private List<NodeResponse> nodes;
    private List<EdgeResponse> edges;
}