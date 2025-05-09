package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostDto(
        Long id,

        @NotNull
        @Size(max = 100)
        String title,

        @NotNull
        @Size(max = 550)
        String content,

        Long author_id,
        String author_name,

        Long topic_id,
        String topic_name,

        LocalDateTime createdAt
) {}
