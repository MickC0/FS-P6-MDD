package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.PostEntity;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {


    public PostDto toDto(PostEntity post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getAuthor().getName(),
                post.getTopic().getId(),
                post.getTopic().getName(),
                post.getCreatedAt()
        );
    }


    public PostEntity toEntity(PostDto dto, UserEntity author, TopicEntity topic) {
        PostEntity post = new PostEntity();
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setAuthor(author);
        post.setTopic(topic);
        return post;
    }
}
