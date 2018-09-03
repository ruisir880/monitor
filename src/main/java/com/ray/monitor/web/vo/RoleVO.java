package com.ray.monitor.web.vo;

import java.io.Serializable;

/**
 * Created by Ray Rui on 9/3/2018.
 */
public class RoleVO implements Serializable{
    private static final long serialVersionUID = -3877180007605850919L;

    private long id;

    private String name;

    public RoleVO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
