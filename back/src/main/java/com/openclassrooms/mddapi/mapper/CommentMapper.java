package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entity.CommentEntity;
import com.openclassrooms.mddapi.entity.PostEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentDto toDto(CommentEntity entity) {
        return new CommentDto(
                entity.getId(),
                entity.getContent(),
                entity.getAuthor().getId(),
                entity.getAuthor().getName(),
                entity.getPost().getId(),
                entity.getCreatedAt()
        );
    }

    public List<CommentDto> toDto(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CommentEntity toEntity(CommentDto dto, UserEntity author, PostEntity post) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.content());
        entity.setAuthor(author);
        entity.setPost(post);
        return entity;
    }
}
