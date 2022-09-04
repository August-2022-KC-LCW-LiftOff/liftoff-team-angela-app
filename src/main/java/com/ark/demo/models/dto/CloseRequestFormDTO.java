package com.ark.demo.models.dto;

public class CloseRequestFormDTO {
    private String closeType;
    private Integer requestId;

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
}
