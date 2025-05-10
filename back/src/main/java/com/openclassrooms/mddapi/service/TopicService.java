package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public interface TopicService {

    List<TopicDto> getAllTopics();

    TopicDto getTopicById(Long id) throws EntityNotFoundException;

    TopicDto createTopic(TopicDto topicDto);

    TopicDto updateTopic(Long id, TopicDto topicDto) throws EntityNotFoundException;

    void deleteTopic(Long id) throws EntityNotFoundException;
}
