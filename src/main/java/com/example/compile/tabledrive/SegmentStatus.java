package com.example.compile.tabledrive;

public class SegmentStatus {
    Integer status;
    String token;
    String action;

    public SegmentStatus() {

    }

    public SegmentStatus(Integer status, String token,String action){
        this.status = status;
        this.token = token;
        this.action = action;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
