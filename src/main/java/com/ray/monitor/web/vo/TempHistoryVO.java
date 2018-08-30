package com.ray.monitor.web.vo;

import com.ray.monitor.utils.ParseUtil;

import java.io.Serializable;
import java.util.Date;

public class TempHistoryVO implements Serializable {
    private static final long serialVersionUID = 8965626012927903691L;

    private double temp;

    private String genTime;

    private String state;

    public TempHistoryVO(double temp, String genTime,String state) {
        this.temp = temp;
        this.genTime = genTime;
        this.state = state;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }


    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}