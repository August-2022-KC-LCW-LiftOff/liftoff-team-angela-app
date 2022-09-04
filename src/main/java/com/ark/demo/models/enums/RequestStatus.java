package com.ark.demo.models.enums;

public enum RequestStatus {
    ACTIVE("Open"),
    INACTIVE("Close");

    private final String displayName;

    RequestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
