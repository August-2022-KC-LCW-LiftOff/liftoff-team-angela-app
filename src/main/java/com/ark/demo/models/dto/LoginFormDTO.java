package com.ark.demo.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginFormDTO {
    @NotNull
    @NotBlank(message = "Username is required.")
    private String username;

    @NotNull
    @NotBlank(message = "Password is required.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
