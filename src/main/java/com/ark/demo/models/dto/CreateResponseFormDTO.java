package com.ark.demo.models.dto;

import com.ark.demo.models.Request;
import com.ark.demo.models.Thread;
import com.ark.demo.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateResponseFormDTO {
    private User user;
//    private Request userRequest;
// getter and setter, add to constructor

    @Size(min=1, max = 800, message = "must be at least 20 characters")
    @NotNull(message = "must include message")
    private String message;
    private Boolean contactSharing;

    private Thread thread;
    private Request request;

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

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
