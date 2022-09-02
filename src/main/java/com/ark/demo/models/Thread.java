package com.ark.demo.models;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

public class Thread extends AbstractEntity {



    @OneToOne
    private Request request;

    @OneToOne(mappedBy = "userThreads")
    private User threadUser;

    @OneToMany(mappedBy = "responseThread")
    private List<Response> threadResponses = new ArrayList<>();


    public Thread() {
    }


    public Thread(Request request, User threadUser) {
        this.request = request;
        this.threadUser = threadUser;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getThreadUser() {
        return threadUser;
    }

    public void setThreadUser(User threadUser) {
        this.threadUser = threadUser;
    }

    public List<Response> getThreadResponses() {
        return threadResponses;
    }

    public void addThreadResponse(Response response){
        this.threadResponses.add(response);
    }
}
