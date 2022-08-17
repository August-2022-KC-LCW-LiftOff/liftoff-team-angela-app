package com.ark.demo.models;


import java.util.ArrayList;
import java.util.List;

public class Response {
    public List<Response> response = new ArrayList<>();
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    private String title;

    private String message;

    public Response() {

    }

    public Response(String title, String message) {
        this.title = title;
        this.message = message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
