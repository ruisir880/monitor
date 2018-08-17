package com.ray.monitor.model;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String username;
    private String realName;
    private String mobile;
    private String email;

    public User(long id, String username, String realName, String mobile, String email) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.mobile = mobile;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}