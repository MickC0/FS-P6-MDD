package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthRequest;
import com.openclassrooms.mddapi.dto.CreateUserRequest;
import com.openclassrooms.mddapi.dto.ModifyUserRequest;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Authentication", description = "Endpoints for user registration, login and profile management")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String TOKEN = "token";

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            AuthenticationManager authManager
    ) {
        this.userService = userService;
        this.jwtService  = jwtService;
        this.authManager = authManager;
    }


    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and immediately authenticates, returning a JWT.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered and JWT returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid registration data")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(
            @Valid @RequestBody CreateUserRequest request
    ) {
      userService.createUser(request);

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String jwt = jwtService.generateToken(auth);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(TOKEN, jwt));
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT for subsequent requests.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful, JWT returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid email or password")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(
            @Valid @RequestBody AuthRequest authRequest
    ) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.email(),
                            authRequest.password()
                    )
            );
            String jwt = jwtService.generateToken(auth);
            return ResponseEntity.ok(Map.of(TOKEN, jwt));
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }
    }

    @Operation(
            summary = "Get current user profile",
            description = "Returns the profile of the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile returned"),
                    @ApiResponse(responseCode = "403", description = "Authentication required")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(
            Authentication auth
    ) {
        String username = auth.getName();
        UserDto profile = userService.getUserProfile(username);
        return ResponseEntity.ok(profile);
    }


    @Operation(
            summary = "Modify current user profile",
            description = "Updates the authenticated user's profile data and returns a refreshed JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated and new JWT returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid update data"),
                    @ApiResponse(responseCode = "403", description = "Authentication required")
            }
    )
    @PutMapping("/me")
    public ResponseEntity<Map<String,String>> modify(
            Authentication auth,
            @Valid @RequestBody ModifyUserRequest request
    ) {
        String username = auth.getName();
        userService.modifyUser(username, request);
        Authentication newAuth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getName(),
                        request.password() != null && !request.password().isBlank()
                                ? request.password() : auth.getCredentials().toString()
                )
        );
        String jwt = jwtService.generateToken(newAuth);
        return ResponseEntity.ok(Map.of(TOKEN, jwt));
    }
}
