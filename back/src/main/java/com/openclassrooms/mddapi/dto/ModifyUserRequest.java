package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ModifyUserRequest(
        @Size(max = 30)
        String name,

        @NotNull
        String oldPassword,

        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}$|null|")
        String password,

        @Size(max = 63)
        @Email
        String email
) {}
