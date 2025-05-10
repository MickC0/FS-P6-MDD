package com.openclassrooms.mddapi.service.impl;


import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entity.PostEntity;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final TopicService topicService;
    private final PostMapper postMapper;
    private final TopicMapper topicMapper;

    public PostServiceImpl(
            PostRepository postRepository,
            UserService userService,
            TopicService topicService,
            PostMapper postMapper, TopicMapper topicMapper
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.topicService = topicService;
        this.postMapper = postMapper;
        this.topicMapper = topicMapper;
    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity entity = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));
        return postMapper.toDto(entity);
    }

    @Override
    public List<PostDto> getPostsByTopics(List<Long> topicIds) {
        List<PostEntity> entities = postRepository.findByTopic_IdIn(topicIds);
        return entities.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto dto) {
        UserEntity author = userService.getUserEntityById(dto.author_id());
        TopicDto topicDto = topicService.getTopicById(dto.topic_id());
        TopicEntity topicEntity = topicMapper.toEntity(topicDto);
        PostEntity toSave = postMapper.toEntity(dto, author, topicEntity);
        PostEntity saved = postRepository.save(toSave);
        return postMapper.toDto(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public PostEntity findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Post not found with id " + id)
                );
    }
}
