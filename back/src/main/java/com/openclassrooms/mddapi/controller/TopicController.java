package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;


@Tag(name = "Topics", description = "Endpoints for managing topics and user subscriptions")
@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final UserService  userService;

    public TopicController(
            TopicService topicService,
            UserService userService
    ) {
        this.topicService = topicService;
        this.userService  = userService;
    }

    @Operation(
            summary     = "List all topics",
            description = "Returns a list of all available programming topics. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "List of topics returned"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("")
    @Secured("ROLE_USER")
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }


    @Operation(
            summary     = "Get topic by ID",
            description = "Retrieves a single topic by its ID. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Topic returned"),
                    @ApiResponse(responseCode = "400", description = "Invalid topic ID"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public TopicDto getTopicById(@PathVariable("id") long id) {
        return topicService.getTopicById(id);
    }

    @Operation(
            summary     = "Create a new topic",
            description = "Creates a new programming topic. Topic names are case-insensitive and trimmed to prevent duplicates. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Topic created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data or duplicate topic name"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PostMapping("")
    @Secured("ROLE_USER")
    public TopicDto createTopic(@RequestBody TopicDto dto) {
        try {
            return topicService.createTopic(dto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e
            );
        }
    }

    @Operation(
            summary     = "Subscribe to a topic",
            description = "Subscribes the authenticated user to the specified topic. Once subscribed, the topic will appear in their personalized feed. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Successfully subscribed"),
                    @ApiResponse(responseCode = "400", description = "Invalid topic ID"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PostMapping("/{id}/subscribe")
    @Secured("ROLE_USER")
    public String subscribeTopic(
            @PathVariable("id") long id,
            Authentication authentication
    ) {
        String username = authentication.getName();

        TopicEntity topicEntity = topicService.getTopicEntityById(id);

        UserDto userDto    = userService.getUserProfile(username);
        UserEntity user    = userService.getUserEntityById(userDto.id());

        Set<TopicEntity> subs = user.getSubscriptions();
        if (subs.add(topicEntity)) {
            userService.updateSubscriptions(username, subs);
            return "Successfully subscribed";
        } else {
            return "User already subscribed to this topic";
        }
    }

    @Operation(
            summary     = "Unsubscribe from a topic",
            description = "Removes the authenticated user's subscription to the specified topic. Requires ROLE_USER.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Successfully unsubscribed"),
                    @ApiResponse(responseCode = "400", description = "Invalid topic ID"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @DeleteMapping("/{id}/subscribe")
    @Secured("ROLE_USER")
    public String unsubscribeTopic(
            @PathVariable("id") long id,
            Authentication authentication
    ) {
        String username = authentication.getName();

        TopicEntity topicEntity = topicService.getTopicEntityById(id);
        UserDto    userDto      = userService.getUserProfile(username);
        UserEntity user         = userService.getUserEntityById(userDto.id());

        Set<TopicEntity> subs = user.getSubscriptions();
        if (subs.remove(topicEntity)) {
            userService.updateSubscriptions(username, subs);
            return "Successfully unsubscribed";
        } else {
            return "User wasn't subscribed to this topic";
        }
    }
}
