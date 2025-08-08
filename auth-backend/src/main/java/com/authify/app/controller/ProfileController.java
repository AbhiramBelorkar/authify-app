package com.authify.app.controller;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;
import com.authify.app.service.EmailService;
import com.authify.app.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    private ProfileService profileService;
    private EmailService emailService;

    public ProfileController(ProfileService profileService,
                             EmailService emailService) {
        this.profileService = profileService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest profileRequest){
        ProfileResponse response = profileService.createProfile(profileRequest);
        emailService.sendWelcomeEmail(response.getEmail(), response.getName());
        return response;
    }

    @GetMapping("/test")
    public String test(){
        return "Authentication is working";
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email){
        return profileService.getProfile(email);
    }
}
