package com.posts.demo.config;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
