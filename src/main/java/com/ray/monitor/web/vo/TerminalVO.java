package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class TerminalVO implements Serializable {
    private static final long serialVersionUID = -3753348935742250557L;

    private long id;

    private String name;

    private String genTime;

    public TerminalVO(long id, String name, String genTime) {
        this.id = id;
        this.name = name;
        this.genTime = genTime;
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

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }
}
