package com.ark.demo.models.dto;

import com.ark.demo.models.User;
import com.ark.demo.models.UserDetails;

import java.util.Date;

public class ViewProfileDTO {
    private UserDetails userDetails;

    private String dateCreated;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
