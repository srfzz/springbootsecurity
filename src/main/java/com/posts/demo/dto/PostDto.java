package com.posts.demo.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PostDto( String title, String content, UUID uuid) {
}
