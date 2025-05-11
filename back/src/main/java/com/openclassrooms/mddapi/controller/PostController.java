package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Posts", description = "Endpoints for creating, retrieving posts and managing their comments")
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

    @Operation(
            summary     = "Retrieve a post by its ID",
            description = "Fetches a single post identified by its ID. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Post returned successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid post ID supplied"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public PostDto getPostById(@PathVariable("id") long id) throws EntityNotFoundException {
        return postService.getPostById(id);
    }

    @Operation(
            summary     = "Create a new comment on a post",
            description = """
            Adds a new comment to the post identified by the given ID.
            The comment text must be non-blank and at most 500 characters.
            Requires ROLE_USER.
            """,
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Comment created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid post ID or comment content"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
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

    @Operation(
            summary     = "List all comments for a post",
            description = "Retrieves all comments associated with the post identified by the given ID. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "List of comments returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid post ID supplied"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("/{id}/comments")
    @Secured("ROLE_USER")
    public List<CommentDto> getCommentsForPost(@PathVariable("id") long id)
            throws EntityNotFoundException {
        return commentService.getCommentsForPost(id);
    }

    @Operation(
            summary     = "Create a new post",
            description = """
            Creates a new post under the topic specified in the request body.
            The authenticated user becomes the author. Requires ROLE_USER.
            """,
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Post created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid post data or topic ID"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
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
