package com.ark.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Thread extends AbstractEntity{




    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;


    @OneToMany(mappedBy = "thread")
    private List<Response> responses = new ArrayList<>();
    @OneToMany(mappedBy = "thread")
    private List<User> threadUsers = new ArrayList<>();
    public Thread() {
    }

    public Thread(User user, Request request) {
        this.user = user;
        this.request = request;
        this.user = user;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getThreadUsers(){
        return threadUsers;
    }

    public void addThreadUsers(User user){
        this.threadUsers.add(user);
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void addResponse(Response response){
        responses.add(response);
    }


}
