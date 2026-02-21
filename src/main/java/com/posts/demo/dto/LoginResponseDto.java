package com.posts.demo.dto;

public record LoginResponseDto(Long id, String accessToken, String refreshToken, String username) {
}
