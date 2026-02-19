package com.posts.demo.mapper;

import com.posts.demo.dto.PostDto;
import com.posts.demo.entities.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto toDto(PostEntity postEntity) {
        return PostDto.builder().title(postEntity.getTitle()).content(postEntity.getContent()).uuid(postEntity.getUuid()).build();

    }
    public PostEntity toEntity(PostDto postDto) {
        return PostEntity.builder().title(postDto.title()).content(postDto.content()).build();
    }
}
