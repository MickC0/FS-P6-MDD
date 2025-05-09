package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthRequest;
import com.openclassrooms.mddapi.dto.CreateUserRequest;
import com.openclassrooms.mddapi.dto.ModifyUserRequest;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

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

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(
            @Valid @RequestBody AuthRequest authRequest
    ) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        String jwt = jwtService.generateToken(auth);
        return ResponseEntity.ok(Map.of(TOKEN, jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(
            Authentication auth
    ) {
        String username = auth.getName();
        UserDto profile = userService.getUserProfile(username);
        return ResponseEntity.ok(profile);
    }

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
