package com.sistema.blog.dto;

import com.sistema.blog.entities.Comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class PostDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should be at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should be at least 10 characters")
    private String description;
    @NotEmpty(message = "content must not be empty or null")
    private String content;

    private Set<Comment> comments;

    public PostDTO() {
        super();
    }

    public PostDTO(Long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
