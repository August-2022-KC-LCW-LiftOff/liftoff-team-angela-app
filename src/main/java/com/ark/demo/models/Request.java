package com.ark.demo.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Request extends AbstractEntity {

    private String title;

    private String description;
//    public enum requestType {wants, needs, problems};

//    public enum priority {low, medium, high};
    private Date dateRequested;
    private Date dueDate;
    private Boolean publicEvent;

    @ManyToOne
    private User user;


    public Request() {
    }

    public Request(String title, String description, User user, Date dueDate ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.user = user;
        this.dateRequested = new Date();
        this.publicEvent = false;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}