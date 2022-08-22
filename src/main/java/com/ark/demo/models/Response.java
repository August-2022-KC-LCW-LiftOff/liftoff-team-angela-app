package com.ark.demo.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Response  {
//    private User user;
//    private Request userRequest;
// getter and setter, add to constructor

    @Size(min=1, max = 800, message = "must be at least 20 characters")
    @NotNull(message = "must include message")
    private String message;
    private Boolean contactSharing;

    public Response() {

    }
    public Response( String message, Boolean contactSharing) {

        this.message = message;
        this.contactSharing = contactSharing;
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
