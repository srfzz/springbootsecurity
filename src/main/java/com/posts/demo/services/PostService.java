package com.posts.demo.services;

import com.posts.demo.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface PostService {
    public List<PostDto> getAllPosts();
    public PostDto getPostById(UUID id);
    public PostDto addPost(PostDto postDto);
    public PostDto updatePost(PostDto postDto,UUID id);
}
