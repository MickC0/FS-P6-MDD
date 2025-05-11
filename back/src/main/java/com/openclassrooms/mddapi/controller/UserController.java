package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(
        name        = "Users",
        description = "Endpoints for retrieving user profiles and their subscribed posts"
)
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PostService postService;

    public UserController(
            UserService userService,
            UserMapper userMapper,
            PostService postService
    ) {
        this.userService = userService;
        this.userMapper  = userMapper;
        this.postService = postService;
    }

    @Operation(
            summary     = "Get user by ID",
            description = "Retrieves personal data for the user identified by the given ID. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "User profile returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid user ID supplied"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public UserDto getUserById(@PathVariable("id") long id) throws EntityNotFoundException {
        UserEntity userEntity = userService.getUserEntityById(id);
        return userMapper.toDto(userEntity);
    }

    @Operation(
            summary     = "Get posts from user subscriptions",
            description = "Lists all posts under the topics to which the specified user is subscribed. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "List of subscribed posts returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid user ID supplied"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("/{id}/posts")
    @Secured("ROLE_USER")
    public List<PostDto> getPostsByUserSubscriptions(@PathVariable("id") long id)
            throws EntityNotFoundException {
        UserEntity userEntity = userService.getUserEntityById(id);
        Set<TopicEntity> subs = userEntity.getSubscriptions();
        List<Long> topicIds = subs.stream()
                .map(TopicEntity::getId)
                .collect(Collectors.toList());
        return postService.getPostsByTopics(topicIds);
    }
}
