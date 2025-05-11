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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final UserService  userService;
    private final TopicMapper  topicMapper;

    public TopicController(
            TopicService topicService,
            UserService userService,
            TopicMapper topicMapper
    ) {
        this.topicService = topicService;
        this.userService  = userService;
        this.topicMapper  = topicMapper;
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }

    @Operation(summary = "Get a topic by its id", responses = {
            @ApiResponse(responseCode = "200", description = "Topic found"),
            @ApiResponse(responseCode = "400", description = "Topic id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public TopicDto getTopicById(@PathVariable("id") long id) {
        return topicService.getTopicById(id);
    }

    @Operation(summary = "Create a new topic", responses = {
            @ApiResponse(responseCode = "200", description = "Topic successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data or duplicate topic"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
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

    @Operation(summary = "Subscribe the user to the requested topic", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully subscribed to topic"),
            @ApiResponse(responseCode = "400", description = "Topic id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/subscribe")
    @Secured("ROLE_USER")
    public String subscribeTopic(
            @PathVariable("id") long id,
            Authentication authentication
    ) {
        String username = authentication.getName();

        // 1) Récupère l'entité Topic persistante
        TopicEntity topicEntity = topicService.getTopicEntityById(id);

        // 2) Récupère l'entité User persistante
        UserDto userDto    = userService.getUserProfile(username);
        UserEntity user    = userService.getUserEntityById(userDto.id());

        // 3) Modifie les subscriptions
        Set<TopicEntity> subs = user.getSubscriptions();
        if (subs.add(topicEntity)) {
            userService.updateSubscriptions(username, subs);
            return "Successfully subscribed";
        } else {
            return "User already subscribed to this topic";
        }
    }

    @Operation(summary = "Unsubscribe the user from the requested topic", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed"),
            @ApiResponse(responseCode = "400", description = "Topic id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
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
