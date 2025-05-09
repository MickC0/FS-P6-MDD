package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.TopicEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDto(
        Long id,

        @NotNull
        @Size(max = 30)
        String name,

        @NotNull
        @Size(max = 63)
        @Email
        String email,

        @NotNull
        String roles,

        Set<TopicEntity> subscriptions
) {}
