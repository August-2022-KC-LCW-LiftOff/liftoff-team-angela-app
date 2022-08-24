package com.ark.demo.models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Response extends AbstractEntity {
    private Date responseDate;


    @ManyToOne
    private User user;
//    private Request userRequest;
// getter and setter, add to constructor
    @NotNull
    private String message;

    private Boolean contactSharing;

    public Response() {

    }
    public Response(User user, String message, Boolean contactSharing) {
        this.responseDate = new Date();
        this.user = user;
        this.message = message;
        this.contactSharing = contactSharing;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getContactSharing() {
        return contactSharing;
    }

    public void setContactSharing(Boolean contactSharing) {
        this.contactSharing = contactSharing;
    }


}
