package com.posts.demo.services;

import com.posts.demo.dto.UserDto;
import com.posts.demo.entities.UserEntity;
import com.posts.demo.exceptions.ResourceAlreadyExistsException;
import com.posts.demo.mapper.UserMapper;
import com.posts.demo.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

    }
    @Transactional(readOnly = true)
    public UserDto signUpUser(UserDto userDto)
    {
       Optional<UserEntity> userEntity= userRepository.findByEmail(userDto.email());
       if(userEntity.isPresent())
       {
           throw new ResourceAlreadyExistsException("This Email is Already Taken : "+userDto.email());
       }
      UserEntity savedUser=userRepository.save(userMapper.toEntity(userDto));
      return userMapper.toDto(savedUser);
    }
}
