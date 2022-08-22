package com.ark.demo.models.dto;

import com.ark.demo.models.User;
import com.ark.demo.models.UserDetails;

public class DeleteFormDTO {
    private User user;
    private UserDetails userDetails;

    private String confirm;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
