package com.posts.demo.controller;

import com.posts.demo.config.ApiResponse;
import com.posts.demo.dto.UserDto;
import com.posts.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class UserController {
    private final UserService userService;
public UserController(UserService userService) {
    this.userService = userService;
}
    @RequestMapping(path="api/v1/auth/users")
    @PostMapping(path = "/")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody UserDto userRequestDto)
    {
      UserDto userDto=userService.signUpUser(userRequestDto);
      ApiResponse<?> apiResponse=ApiResponse.builder().success(true).message("success").data(userDto)
              .status(String.valueOf(HttpStatus.CREATED.value()))
              .timestamp(LocalDateTime.now())
              .build();
      return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
