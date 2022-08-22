package com.ark.demo.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class User extends AbstractEntity{
    private Date dateCreated;
    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private UserDetails userDetails;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String username, String password){
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.dateCreated = new Date();
    }

    public User(){
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setPwHash(String password){
        this.pwHash = encoder.encode(password);
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isMatchingPassword(String password){
        return encoder.matches(password,pwHash);
    }
}
