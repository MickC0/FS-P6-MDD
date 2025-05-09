package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {}
