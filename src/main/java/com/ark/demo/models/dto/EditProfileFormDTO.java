package com.ark.demo.models.dto;

import com.ark.demo.models.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditProfileFormDTO {
    @NotNull
    private UserDetails userDetails;
    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
