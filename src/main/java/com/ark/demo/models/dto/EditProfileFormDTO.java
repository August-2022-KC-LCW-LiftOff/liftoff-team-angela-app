package com.ark.demo.models.dto;

import com.ark.demo.models.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditProfileFormDTO {
    private String icon;
    @NotNull
    private UserDetails userDetails;
    public UserDetails getUserDetails() {
        return userDetails;
    }

    private String location;

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
