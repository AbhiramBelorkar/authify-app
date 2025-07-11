package com.authify.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReponse {

    private String userId;
    private String name;
    private String email;
    private Boolean isAccountVerified;

}
