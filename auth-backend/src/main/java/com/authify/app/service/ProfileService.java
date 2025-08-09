package com.authify.app.service;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest req);

    ProfileResponse getProfile(String email);

    void sendResetOtp(String email);

    void resetPassword(String email, String otp, String newPassword);

    void sendOtp(String email);

    void verifyOtp(String email, String otp);
}
