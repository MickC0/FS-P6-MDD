package com.openclassrooms.mddapi.dto;

public record ErrorResponse(String error, int status, String reason) {
}

