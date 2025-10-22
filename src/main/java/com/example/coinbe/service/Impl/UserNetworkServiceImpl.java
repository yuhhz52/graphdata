package com.example.coinbe.service.Impl;

import com.example.coinbe.dto.response.*;
import com.example.coinbe.entity.Chapter;
import com.example.coinbe.entity.Enum.EdgeType;
import com.example.coinbe.entity.Enum.NodeType;
import com.example.coinbe.entity.Squat;
import com.example.coinbe.entity.User;
import com.example.coinbe.exception.AppException;
import com.example.coinbe.exception.ErrorCode;
import com.example.coinbe.repository.ChapterRepository;
import com.example.coinbe.repository.SquatRepository;
import com.example.coinbe.repository.UserRepository;
import com.example.coinbe.service.UserNetworkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserNetworkServiceImpl implements UserNetworkService {
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final SquatRepository squatRepository;

    public NetworkResponse getUserNetwork(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Map<UUID, NodeResponse> nodeMap = new LinkedHashMap<>();
        List<EdgeResponse> edges = new ArrayList<>();

        // --- User node ---
        nodeMap.put(user.getId(), NodeResponse.builder()
                .id(user.getId())
                .type(NodeType.USER.name())
                .label(user.getFullName())
                .description(user.getTitle())
                .build());

        // --- Chapter & Clan nodes ---
        Optional.ofNullable(user.getChapter()).ifPresent(chapter -> {
            nodeMap.put(chapter.getId(), NodeResponse.builder()
                    .id(chapter.getId())
                    .type(NodeType.CHAPTER.name())
                    .label(chapter.getName())
                    .description(chapter.getDescription())
                    .build());

            edges.add(EdgeResponse.builder()
                    .source(user.getId())
                    .target(chapter.getId())
                    .relation(EdgeType.BELONGS_TO.name())
                    .build());

            Optional.ofNullable(chapter.getClan()).ifPresent(clan -> {
                nodeMap.put(clan.getId(), NodeResponse.builder()
                        .id(clan.getId())
                        .type(NodeType.CLAN.name())
                        .label(clan.getName())
                        .description(clan.getDescription())
                        .build());

                edges.add(EdgeResponse.builder()
                        .source(chapter.getId())
                        .target(clan.getId())
                        .relation(EdgeType.PART_OF.name())
                        .build());
            });
        });

        // --- Squat nodes ---
        Optional.ofNullable(user.getSquats()).orElse(Collections.emptySet()).forEach(squat -> {
            nodeMap.put(squat.getId(), NodeResponse.builder()
                    .id(squat.getId())
                    .type(NodeType.SQUAT.name())
                    .label(squat.getName())
                    .description(squat.getTopic())
                    .build());

            edges.add(EdgeResponse.builder()
                    .source(user.getId())
                    .target(squat.getId())
                    .relation(EdgeType.MEMBER_OF.name())
                    .build());
        });

        return NetworkResponse.builder()
                .nodes(new ArrayList<>(nodeMap.values()))
                .edges(edges)
                .build();
    }


    public ChapterNetworkResponse getChapterNetwork(UUID chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        List<UserResponse> members = userRepository.findByChapterId(chapterId)
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .title(user.getTitle())
                        .build())
                .collect(Collectors.toList());

        // Tạo edges user → chapter
        List<EdgeResponse> edges = members.stream()
                .map(user -> EdgeResponse.builder()
                        .source(user.getId())
                        .target(chapter.getId())
                        .relation(EdgeType.MEMBER_OF.name())
                        .build())
                .collect(Collectors.toList());

        return ChapterNetworkResponse.builder()
                .chapterId(chapter.getId())
                .chapterName(chapter.getName())
                .chapterTopic(chapter.getDescription())
                .members(members)
                .edges(edges)
                .build();
    }


    public SquatNetworkResponse getSquatNetwork(UUID squatId) {
        Squat squat = squatRepository.findById(squatId)
                .orElseThrow(() -> new AppException(ErrorCode.SQUAT_NOT_FOUND));

        List<UserResponse> members = userRepository.findBySquatsId(squatId)
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .title(user.getTitle())
                        .build())
                .collect(Collectors.toList());

        List<EdgeResponse> edges = members.stream()
                .map(user -> EdgeResponse.builder()
                        .source(user.getId())
                        .target(squat.getId())
                        .relation(EdgeType.MEMBER_OF.name())
                        .build())
                .collect(Collectors.toList());

        return SquatNetworkResponse.builder()
                .squatId(squat.getId())
                .squatName(squat.getName())
                .squatTopic(squat.getTopic())
                .members(members)
                .edges(edges)
                .build();
    }


}