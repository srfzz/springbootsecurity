package com.posts.demo.services;


import com.posts.demo.dto.PostDto;
import com.posts.demo.entities.PostEntity;
import com.posts.demo.exceptions.ResourceNotFoundException;
import com.posts.demo.mapper.PostMapper;
import com.posts.demo.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    Logger log= LoggerFactory.getLogger(PostServiceImpl.class
    );
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MapperBuilder mapperBuilder;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, MapperBuilder mapperBuilder) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.mapperBuilder = mapperBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        log.debug("In getAllPosts()");
        log.trace("In getAllPosts():{}",postRepository.findAll());
        log.info("In getAllPosts():{}",postRepository.findAll());
        log.error("In getAllPosts():{}",postRepository.findAll());
        log.warn("kwbnbndb");
        return postRepository.findAll().stream().map(postMapper::toDto).toList();

    }



    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(UUID id) {
       PostEntity postEntity = postRepository.findByUuid(id).orElseThrow(()->new ResourceNotFoundException("This Post Doesn't Exist:"+ id));
        return postMapper.toDto(postEntity);
    }

    @Override
    @Transactional()
    public PostDto addPost(PostDto postDto) {
        PostEntity postEntity = postMapper.toEntity(postDto);
        PostEntity savedEntity =postRepository.save(postEntity);
        return postMapper.toDto(savedEntity);
    }

    @Override
    public PostDto updatePost(PostDto postDto, UUID id) {
        PostEntity postEntity=postRepository.findByUuid(id).orElseThrow(()->new ResourceNotFoundException("This Post Doesn't Exist:"+ id));
        postEntity.setUuid(id);
        return postMapper.toDto(postRepository.save(postMapper.toEntity(postDto)));
    }
}
