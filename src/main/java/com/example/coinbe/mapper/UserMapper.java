package com.example.coinbe.mapper;

import com.example.coinbe.dto.request.UserCreationRequest;
import com.example.coinbe.dto.response.UserResponse;
import com.example.coinbe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);



}