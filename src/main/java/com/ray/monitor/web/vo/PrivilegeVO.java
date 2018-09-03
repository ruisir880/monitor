package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Rui on 9/3/2018.
 */
public class PrivilegeVO implements Serializable{
    private static final long serialVersionUID = -4787134676797144683L;

    private long id;

    private String name;

    public PrivilegeVO(long id, String name) {
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
