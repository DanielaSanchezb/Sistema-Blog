package com.sistema.blog.service;

import com.sistema.blog.dto.PostDTO;
import com.sistema.blog.dto.PostResponse;


public interface PostService {

    public PostDTO createPost(PostDTO postDTO);

    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    public PostDTO getPostById(long id);

    public PostDTO updatePost(PostDTO postDTO, long id);

    public void deletePost(long id);

}
