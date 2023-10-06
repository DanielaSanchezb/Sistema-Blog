package com.sistema.blog.service;

import com.sistema.blog.dto.CommentDTO;
import com.sistema.blog.entities.Comment;
import com.sistema.blog.entities.Posts;
import com.sistema.blog.exceptions.BlogAppException;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.CommentRepository;
import com.sistema.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapperEntity(commentDTO);
        Posts post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return  mapperDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapperDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, long commentId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST , "comment does not belong to the post");
        }

        return mapperDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST , "comment does not belong to the post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment commentUpdated = commentRepository.save(comment);

        return mapperDTO(commentUpdated);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST , "comment does not belong to the post");
        }

        commentRepository.delete(comment);
    }

    private CommentDTO mapperDTO(Comment comment){
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        return commentDTO;
    }

    private Comment mapperEntity(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        return comment;

    }
}
