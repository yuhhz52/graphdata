package com.example.coinbe.service;

import com.example.coinbe.dto.response.*;
import com.example.coinbe.entity.*;
import com.example.coinbe.entity.Enum.EdgeType;
import com.example.coinbe.entity.Enum.NodeType;
import com.example.coinbe.exception.AppException;
import com.example.coinbe.exception.ErrorCode;
import com.example.coinbe.mapper.UserMapper;
import com.example.coinbe.repository.ChapterRepository;
import com.example.coinbe.repository.SquatRepository;
import com.example.coinbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


public interface UserNetworkService {

    public NetworkResponse getUserNetwork(UUID userId);

    public ChapterNetworkResponse getChapterNetwork(UUID chapterId);

    public SquatNetworkResponse getSquatNetwork(UUID squatId);


}