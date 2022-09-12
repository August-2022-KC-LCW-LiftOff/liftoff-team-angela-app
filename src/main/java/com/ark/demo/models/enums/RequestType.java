package com.ark.demo.models.enums;

public enum RequestType {


    WANTS("Wants"),
    NEEDS("Needs"),
    PROBLEMS("Problems");

    private final String displayName;

    RequestType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}


