package com.ark.demo.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Thread extends AbstractEntity {


    @OneToOne
    private Request request;

    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "thread")
    private List<Response> threadResponses = new ArrayList<>();

    @OneToMany(mappedBy = "thread")
    private List<User> threadUsers = new ArrayList<>();

    public Thread() {
    }


    public Thread(Request request, User user) {
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

    public List<Response> getThreadResponses() {
        return threadResponses;
    }

    public void addThreadResponse(Response response) {
        this.threadResponses.add(response);
    }




}
