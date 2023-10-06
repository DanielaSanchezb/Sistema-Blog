package com.sistema.blog.service;

import com.sistema.blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(long postId, CommentDTO commentDTO);

    public List<CommentDTO> getCommentByPostId(long postId);

    public CommentDTO getCommentById(Long postId,long commentId);

    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest);

    public void deleteComment(Long postId, Long commentId);
}
