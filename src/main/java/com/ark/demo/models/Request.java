package com.ark.demo.models;

import com.ark.demo.models.enums.PriorityLevel;
import com.ark.demo.models.enums.RequestType;

import com.ark.demo.models.enums.RequestStatus;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Request extends AbstractEntity {

    private String title;

    private String description;

    private String location;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;


    private Date dateRequested;
    private Date dueDate;
    private Boolean publicEvent;
    private RequestType type;
    private PriorityLevel level;

    private RequestStatus status;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "request")
    private List<Thread> threads = new ArrayList<>();

    public Request() {
    }

    public Request(String title, String description, String addressLine1, String addressLine2, String city, String state, String zipcode, Date dueDate, Boolean publicEvent, String location, RequestType type, PriorityLevel level) {
        this.title = title;
        this.description = description;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.dueDate = dueDate;
        this.publicEvent = publicEvent;
        this.dateRequested = new Date();
        this.location = location;
        this.type = type;
        this.level = level;

        this.status = RequestStatus.ACTIVE;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String requestLocation) {
        this.location = requestLocation;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<Thread> getThreads() {
        return threads;

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }


    public List<Thread> getResponseThreads() {
        return responseThreads;
    }

    public void addThread(Thread thread){
        threads.add(thread);
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public PriorityLevel getLevel() {
        return level;
    }

    public void setLevel(PriorityLevel level) {
        this.level = level;
    }
}
