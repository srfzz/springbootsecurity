package com.posts.demo.controller;

import com.posts.demo.config.ApiResponse;
import com.posts.demo.dto.PostDto;
import com.posts.demo.services.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")

    public ResponseEntity<ApiResponse<?>> getAllPosts() {
       List<PostDto> postDto=postService.getAllPosts();
       ApiResponse<Object> response=ApiResponse.builder().success(true).status(String.valueOf(HttpStatus.OK.value())).data(postDto).timestamp(LocalDateTime.now()).message("Successful").build();
       return ResponseEntity.ok(response);
    }

    @PostMapping("/posts")

    public ResponseEntity<ApiResponse<?>> addPost(@Valid @RequestBody PostDto postRequestDto) {
        PostDto postDto=postService.addPost(postRequestDto);
        ApiResponse<?> apiResponse=ApiResponse.builder().success(true).message("successful").status(String.valueOf(HttpStatus.CREATED.value())).data(postDto).timestamp(LocalDateTime.now()).build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/posts/{postId}")

    public ResponseEntity<ApiResponse<?>> getPost(@PathVariable("postId") UUID postId) {
      PostDto postDto= postService.getPostById(postId);
        ApiResponse<?> apiResponse=ApiResponse.builder().success(true).message("successful").status(String.valueOf(HttpStatus.OK.value())).data(postDto).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PutMapping("posts/{postId}")
    public ResponseEntity<ApiResponse<?>> updatePost(@Valid @RequestBody PostDto postRequestDto, @PathVariable("postId") UUID postId)
    {
        PostDto postDto=postService.updatePost(postRequestDto,postId);
        ApiResponse<?> apiResponse=ApiResponse.builder().success(true).message("successful").status(String.valueOf(HttpStatus.OK.value())).data(postDto).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
