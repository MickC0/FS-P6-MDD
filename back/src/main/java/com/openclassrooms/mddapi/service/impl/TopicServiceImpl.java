package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.service.TopicService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(
            TopicRepository topicRepository,
            TopicMapper topicMapper
    ) {
        this.topicRepository = topicRepository;
        this.topicMapper     = topicMapper;
    }

    @Override
    public List<TopicDto> getAllTopics() {
        List<TopicEntity> entities = topicRepository.findAll();
        return entities.stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TopicDto getTopicById(Long id) {
        TopicEntity entity = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id " + id));
        return topicMapper.toDto(entity);
    }

    @Override
    public TopicDto createTopic(TopicDto dto) {
        String norm = dto.name().trim().toLowerCase();
        if (topicRepository.existsByNormalizedName(norm)) {
            throw new IllegalArgumentException(
                    "Un topic existe déjà avec ce nom : " + dto.name()
            );
        }
        TopicEntity toSave = topicMapper.toEntity(dto);
        TopicEntity saved  = topicRepository.save(toSave);
        return topicMapper.toDto(saved);
    }

    @Override
    public TopicDto updateTopic(Long id, TopicDto dto) {
        TopicEntity existing = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id " + id));
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        TopicEntity updated = topicRepository.save(existing);
        return topicMapper.toDto(updated);
    }

    @Override
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new EntityNotFoundException("Topic not found with id " + id);
        }
        topicRepository.deleteById(id);
    }
}