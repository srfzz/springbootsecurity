package com.posts.demo.services;

import com.posts.demo.dto.LoginDto;
import com.posts.demo.dto.LoginResponseDto;
import com.posts.demo.entities.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(@Lazy AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public LoginResponseDto userSignIn(LoginDto userLoginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.email(),userLoginDto.password())
        );
        UserEntity userEntity=(UserEntity) authentication.getPrincipal();
        assert userEntity != null;
        String jwtAccessToken=jwtService.generateAccessToken(userEntity);
        String jwtRefreshToken=jwtService.generateRefreshToken(userEntity);
        return new LoginResponseDto(userEntity.getId(),jwtAccessToken,jwtRefreshToken,userEntity.getName());
    }

    public LoginResponseDto refreshToken(String refreshToken) {
       Long userId = jwtService.getUserIdFromToken(refreshToken);
       UserEntity user=userService.gerUserById(userId);
        String jwtAccessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),jwtAccessToken,refreshToken,user.getName());


    }
}
