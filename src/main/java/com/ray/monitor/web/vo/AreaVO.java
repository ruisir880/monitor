package com.ray.monitor.web.vo;

import java.io.Serializable;

/**
 * Created by Ray Rui on 8/29/2018.
 */
public class AreaVO implements Serializable {
    private static final long serialVersionUID = -6944973096474617330L;

    private long id;

    private String name;

    public AreaVO(long id, String name) {
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
