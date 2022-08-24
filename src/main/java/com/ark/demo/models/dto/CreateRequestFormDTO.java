package com.ark.demo.models.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class CreateRequestFormDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;


    private String description;
    private Date dateRequested;

//    @NotBlank(message = "Due date is required")
    @Future(message = "Date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private Boolean publicEvent;


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

    public Date getDateRequested() {
        return dateRequested;
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
}
