package com.authify.app.service;

import com.authify.app.dto.ProfileResponse;
import com.authify.app.dto.ProfileRequest;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest req);
}
