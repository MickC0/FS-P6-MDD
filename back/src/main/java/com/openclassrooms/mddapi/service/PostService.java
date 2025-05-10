package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.PostEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PostService {

    PostDto getPostById(Long id) throws EntityNotFoundException;

    List<PostDto> getPostsByTopics(List<Long> topicIds);

    PostDto createPost(PostDto postDto) throws EntityNotFoundException;

    boolean existsById(Long id);

    PostEntity findPostById(Long id);
}
