package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "get topics", responses = {
            @ApiResponse(responseCode = "200", description = "The list of topics is available"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public TopicDto getTopicById(@PathVariable("id") long id) throws EntityNotFoundException {
        return topicService.getTopicById(id);
    }

    @Operation(summary = "Create a new topic", responses = {
            @ApiResponse(responseCode = "200", description = "Topic successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @PostMapping("")
    @Secured("ROLE_USER")
    public TopicDto createTopic(@RequestBody TopicDto dto) {
        return topicService.createTopic(dto);
    }

    @Operation(summary = "Subscribe the user to requested topic", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully subscribed to topic"),
            @ApiResponse(responseCode = "400", description = "Topic Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/subscribe")
    @Secured("ROLE_USER")
    public String subscribeTopic(
            @PathVariable("id") long id,
            Authentication authentication
    ) throws EntityNotFoundException {
        String username = authentication.getName();
        TopicDto  topicDto    = topicService.getTopicById(id);
        TopicEntity topicEntity = topicMapper.toEntity(topicDto);
        UserDto         userDto = userService.getUserProfile(username);
        Set<TopicEntity> subs    = userDto.subscriptions();
        if (subs.add(topicEntity)) {
            userService.updateSubscriptions(username, subs);
            return "Successfully subscribed";
        }
        return "User already subscribed to this topic";
    }

    @Operation(summary = "Unsubscribe the user from requested topic", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed"),
            @ApiResponse(responseCode = "400", description = "Topic Id doesn't exist"),
            @ApiResponse(responseCode = "403", description = "Access unauthorized")
    })
    @DeleteMapping("/{id}/subscribe")
    @Secured("ROLE_USER")
    public String unsubscribeTopic(
            @PathVariable("id") long id,
            Authentication authentication
    ) throws EntityNotFoundException {
        String username = authentication.getName();
        TopicDto    topicDto    = topicService.getTopicById(id);
        TopicEntity topicEntity = topicMapper.toEntity(topicDto);
        UserDto         userDto = userService.getUserProfile(username);
        Set<TopicEntity> subs    = userDto.subscriptions();
        if (subs.remove(topicEntity)) {
            userService.updateSubscriptions(username, subs);
            return "Successfully unsubscribed";
        }
        return "User wasn't subscribed to this topic";
    }
}
