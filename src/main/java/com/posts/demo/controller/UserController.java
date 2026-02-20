package com.posts.demo.controller;

import com.posts.demo.config.ApiResponse;
import com.posts.demo.dto.LoginDto;
import com.posts.demo.dto.LoginResposeDto;
import com.posts.demo.dto.UserDto;
import com.posts.demo.services.AuthService;
import com.posts.demo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping(path="/api/v1/auth/")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
public UserController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
}

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody UserDto userRequestDto)
    {
      UserDto userDto=userService.signUpUser(userRequestDto);
      ApiResponse<?> apiResponse=ApiResponse.builder().success(true).message("success").data(userDto)
              .status(String.valueOf(HttpStatus.CREATED.value()))
              .timestamp(LocalDateTime.now())
              .build();
      return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PostMapping("/login")

    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDto userLoginDto, HttpServletResponse httpresponse) {

        String token = authService.userSignIn(userLoginDto);
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpresponse.addCookie(cookie);
        LoginResposeDto loginResposeDto=new LoginResposeDto(token);
        ApiResponse<?> response = ApiResponse.builder()
                .success(true)
                .message("Login successful")
                .data(loginResposeDto)
                .status(String.valueOf(HttpStatus.OK.value()))
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}
