package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface CommentService {

    CommentDto getCommentById(Long id) throws EntityNotFoundException;

    List<CommentDto> getCommentsForPost(Long postId) throws EntityNotFoundException;

    CommentDto createComment(CommentDto commentDto) throws EntityNotFoundException;

    CommentDto updateComment(Long id, CommentDto commentDto) throws EntityNotFoundException;

    void deleteComment(Long id) throws EntityNotFoundException;
}

