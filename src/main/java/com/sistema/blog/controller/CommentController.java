package com.sistema.blog.controller;

import com.sistema.blog.dto.CommentDTO;
import com.sistema.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> listCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") long commentId){

        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentDTO,HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable(value = "postId") long postId,
                                                  @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") long commentId,
                                                     @Valid @RequestBody CommentDTO commentDTO){

        CommentDTO commentUpdated = commentService.updateComment(postId, commentId, commentDTO);

        return new ResponseEntity<>(commentUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") long commentId){

        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
