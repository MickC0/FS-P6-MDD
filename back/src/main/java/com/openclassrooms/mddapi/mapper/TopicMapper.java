package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper {

    public TopicDto toDto(TopicEntity entity) {
        return new TopicDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public List<TopicDto> toDto(List<TopicEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TopicEntity toEntity(TopicDto dto) {
        TopicEntity entity = new TopicEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        return entity;
    }
}