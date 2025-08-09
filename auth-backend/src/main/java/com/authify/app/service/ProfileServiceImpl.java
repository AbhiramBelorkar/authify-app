package com.authify.app.service;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;
import com.authify.app.entity.UserEntity;
import com.authify.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest req) {
        UserEntity newProfile = convertUserEntity(req);
        if(!userRepository.existsByEmail(req.getEmail())){
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered!");
    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return convertToProfileResponse(existingUser);
    }

    @Override
    public void sendResetOtp(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));

        // Generate 6 Digit OTP
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 1000000));

        // Calculate expiry time (current time + 15 minutes in miliseconds)
        long expiryTime = System.currentTimeMillis() +(15 * 60 * 1000);

        // Update the profile/user
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(expiryTime);

        // Save existing user
        userRepository.save(existingUser);

        try{
            emailService.sendResetOtpEmail(existingUser.getEmail(), otp);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));

        if(existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP.");
        }

        if(existingUser.getResetOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired.");
        }

        // Update the user entity
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);
    }

    @Override
    public void sendOtp(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));

        if(existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()){
            return;
        }

        // Generate 6 Digit OTP
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 1000000));

        // Calculate expiry time (current time + 24 hrs in miliseconds)
        long expiryTime = System.currentTimeMillis() +(24 * 60 * 60 * 1000);

        // Update the user entity
        existingUser.setVerifyOtp(otp);
        existingUser.setVerifyOtpExpiredAt(expiryTime);

        userRepository.save(existingUser);

        try{
            emailService.sendOtpEmail(existingUser.getEmail(), otp);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));

        if(existingUser.getVerifyOtp() == null || !existingUser.getVerifyOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP.");
        }

        if(existingUser.getVerifyOtpExpiredAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired.");
        }

        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpiredAt(0L);

        userRepository.save(existingUser);
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .email(newProfile.getEmail())
                .name(newProfile.getName())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity  convertUserEntity(ProfileRequest req) {
        return UserEntity.builder()
                .email(req.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .resetOtp(null)
                .build();
    }


}
