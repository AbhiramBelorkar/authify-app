package com.authify.app.controller;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;
import com.authify.app.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest profileRequest){
        ProfileResponse response = profileService.createProfile(profileRequest);
        return response;
    }

    @GetMapping("/test")
    public String test(){
        return "Authentication is working";
    }
}
