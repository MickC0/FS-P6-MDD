package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record CommentDto(
        Long id,

        @NotNull
        @Size(max = 500)
        String content,

        @NotNull
        Long author_id,
        String author_name,

        @NotNull
        Long post_id,

        LocalDateTime createdAt
) {}
