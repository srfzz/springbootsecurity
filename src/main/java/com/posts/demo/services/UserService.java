package com.posts.demo.services;

import com.posts.demo.dto.LoginDto;
import com.posts.demo.dto.UserDto;
import com.posts.demo.entities.UserEntity;
import com.posts.demo.exceptions.ResourceAlreadyExistsException;
import com.posts.demo.exceptions.ResourceNotFoundException;
import com.posts.demo.mapper.UserMapper;
import com.posts.demo.repository.UserRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

    }
    public UserEntity getUserByEmail(String email) {
       return userRepository.findByEmail(email).orElse(null);
    }
    public UserEntity gerUserById(Long id) {
        return  userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
    @Transactional
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
    @Transactional()
    public UserDto signUpUser(UserDto userDto)
    {
       Optional<UserEntity> userEntity= userRepository.findByEmail(userDto.email());
       if(userEntity.isPresent())
       {
           throw new ResourceAlreadyExistsException("This Email is Already Taken : "+userDto.email());
       }
       UserEntity userToBeSaved=userMapper.toEntity(userDto);
       userToBeSaved.setPassword(passwordEncoder.encode(userDto.password()));
       userToBeSaved.setName(Jsoup.clean(userDto.name(),Safelist.none()));
      UserEntity savedUser=userRepository.save(userToBeSaved);
      return userMapper.toDto(savedUser);
    }


}
