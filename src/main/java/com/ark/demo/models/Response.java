package com.ark.demo.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Response extends AbstractEntity {
    private Date responseDate;


    @ManyToOne
    private User user;

    @NotNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "threadResponse")
    private Thread thread;


    public Response() {

    }
    public Response(User user, String message, Boolean contactSharing) {
        this.responseDate = new Date();
        this.user = user;
        this.message = message;
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

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
