package com.posts.demo.dto;

import com.posts.demo.entities.enums.Permissions;
import com.posts.demo.entities.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto(
       Long id,
        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z\\s]{3,}$",
                message = "Name must be at least 3 characters long and contain only letters and spaces"
        )
        String name,
       @NotBlank
        @Email
        String email,
       @NotBlank
       @Size(min=8)
       @Pattern(
               regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
               message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character"
       )
        String password,
       Set<Role> roles,
       Set<Permissions> permissions
) {
}
