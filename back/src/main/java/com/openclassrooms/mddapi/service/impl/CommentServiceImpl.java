package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entity.CommentEntity;
import com.openclassrooms.mddapi.entity.PostEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository, PostService postService,
            UserService userService,
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService       = userService;
        this.commentMapper     = commentMapper;
    }

    @Override
    public CommentDto getCommentById(Long id) {
        CommentEntity entity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + id));
        return commentMapper.toDto(entity);
    }

    @Override
    public List<CommentDto> getCommentsForPost(Long postId) {
        if (!postService.existsById(postId)) {
            throw new EntityNotFoundException("Post not found with id " + postId);
        }
        List<CommentEntity> entities = commentRepository.findByPostId(postId);
        return commentMapper.toDto(entities);
    }

    @Override
    public CommentDto createComment(CommentDto dto) {

        UserEntity author = userService.getUserEntityById(dto.author_id());
        PostEntity post   = postService.findPostById(dto.post_id());
        CommentEntity toSave = commentMapper.toEntity(dto, author, post);
        CommentEntity saved  = commentRepository.save(toSave);
        return commentMapper.toDto(saved);
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto dto) {
        CommentEntity existing = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + id));
        existing.setContent(dto.content());
        CommentEntity updated = commentRepository.save(existing);
        return commentMapper.toDto(updated);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment not found with id " + id);
        }
        commentRepository.deleteById(id);
    }
}
