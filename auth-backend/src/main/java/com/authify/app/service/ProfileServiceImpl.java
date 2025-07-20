package com.authify.app.service;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;
import com.authify.app.entity.UserEntity;
import com.authify.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

    @Override
    public ProfileResponse createProfile(ProfileRequest req) {
        UserEntity newProfile = convertUserEntity(req);
        if(!userRepository.existsByEmail(req.getEmail())){
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered!");
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .email(newProfile.getEmail())
                .name(newProfile.getName())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity convertUserEntity(ProfileRequest req) {
        return UserEntity.builder()
                .email(req.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(req.getName())
                .password(req.getPassword())
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .resetOtp(null)
                .build();
    }


}
