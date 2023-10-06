package com.sistema.blog.service;

import com.sistema.blog.dto.PostDTO;
import com.sistema.blog.dto.PostResponse;
import com.sistema.blog.entities.Posts;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Posts post = mapperEntity(postDTO);
        Posts newPost = postRepository.save(post);
        PostDTO postResponse = mapperDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //sort by ascending and descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<Posts> posts = postRepository.findAll(pageable);

        List<Posts> listOfPosts = posts.getContent();
        List <PostDTO> content = listOfPosts.stream().map(post -> mapperDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Posts post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapperDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Posts post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Posts postUpdate = postRepository.save(post);

        return mapperDTO(postUpdate);
    }

    @Override
    public void deletePost(long id) {
        Posts post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    /* convert from entity to DTO*/
    private PostDTO mapperDTO(Posts post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    /* convert from DTO to entity*/
    private Posts mapperEntity(PostDTO postDTO){
        Posts post = modelMapper.map(postDTO, Posts.class);
        return post;
    }

}
