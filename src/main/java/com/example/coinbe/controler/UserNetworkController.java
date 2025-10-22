package com.example.coinbe.controler;

import com.example.coinbe.dto.response.ChapterNetworkResponse;
import com.example.coinbe.dto.response.NetworkResponse;
import com.example.coinbe.dto.response.SquatNetworkResponse;
import com.example.coinbe.security.service.UserDetailsImpl;
import com.example.coinbe.service.UserNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/network")
@RequiredArgsConstructor
public class UserNetworkController {

    private final UserNetworkService userNetworkService;

    // Xem network của 1 user cụ thể
    @GetMapping("/{userId}")
    public NetworkResponse getUserNetwork(@PathVariable UUID userId) {
        return userNetworkService.getUserNetwork(userId);
    }

    // Xem network của chính mình
    @GetMapping("/me")
    public NetworkResponse getMyNetwork() {
        UUID currentUserId = getCurrentUserId();
        return userNetworkService.getUserNetwork(currentUserId);
    }

    // Xem thông tin Chapter + danh sách user trong Chapter đó
    @GetMapping("/chapter/{chapterId}")
    public ChapterNetworkResponse getChapterNetwork(@PathVariable UUID chapterId) {
        return userNetworkService.getChapterNetwork(chapterId);
    }

    // Xem thông tin Squat + danh sách user trong Squat đó
    @GetMapping("/squat/{squatId}")
    public SquatNetworkResponse getSquatNetwork(@PathVariable UUID squatId) {
        return userNetworkService.getSquatNetwork(squatId);
    }

    private UUID getCurrentUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getId();
    }


}