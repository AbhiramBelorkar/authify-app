package com.authify.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "Name should not be empty")
    private String name;
    @Size(min = 6, message = "Password must be atleast 6 characters")
    private String password;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    private String email;
}
