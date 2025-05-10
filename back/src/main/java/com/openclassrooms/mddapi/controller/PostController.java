package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    public PostController(
            PostService postService,
            UserService userService,
            CommentService commentService
    ) {
        this.postService     = postService;
        this.userService = userService;
        this.commentService  = commentService;
    }

    @Operation(summary = "Get Post with its id", responses = {
            @ApiResponse(responseCode = "200", description = "Selected post displayed"),
            @ApiResponse(responseCode = "400", description = "Post's Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public PostDto getPostById(@PathVariable("id") long id) throws EntityNotFoundException {
        return postService.getPostById(id);
    }

    @Operation(summary = "Create new comment for corresponding post", responses = {
            @ApiResponse(responseCode = "200", description = "Comments successfully created"),
            @ApiResponse(responseCode = "400", description = "Post Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/comments")
    @Secured("ROLE_USER")
    public Map<String, String> createComment(
            @PathVariable("id") long id,
            @RequestBody @NotBlank @Size(max = 500) String content,
            Authentication authentication
    ) throws EntityNotFoundException {
        String username = authentication.getName();
        UserDto user = userService.getUserProfile(username);
        CommentDto dto = new CommentDto(
                null,
                content,
                user.id(),
                user.name(),
                id,
                null
        );

        commentService.createComment(dto);
        return Map.of("response", "Your comment has been posted");
    }

    @Operation(summary = "Shows all comments corresponding to a post", responses = {
            @ApiResponse(responseCode = "200", description = "Comments successfully shown"),
            @ApiResponse(responseCode = "400", description = "Post Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @GetMapping("/{id}/comments")
    @Secured("ROLE_USER")
    public List<CommentDto> getCommentsForPost(@PathVariable("id") long id)
            throws EntityNotFoundException {
        return commentService.getCommentsForPost(id);
    }

    @Operation(summary = "Create new post", responses = {
            @ApiResponse(responseCode = "200", description = "Post successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data or Topic Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @PostMapping("")
    @Secured("ROLE_USER")
    public PostDto createPost(
            @RequestBody @Valid PostDto postDto,
            Authentication authentication
    ) throws EntityNotFoundException {
        String username = authentication.getName();

        UserDto user = userService.getUserProfile(username);

        PostDto toCreate = new PostDto(
                null,
                postDto.title(),
                postDto.content(),
                user.id(),
                null,
                postDto.topic_id(),
                null,
                null
        );

        return postService.createPost(toCreate);
    }

}
