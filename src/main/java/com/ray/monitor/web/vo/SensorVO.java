package com.ray.monitor.web.vo;

import java.io.Serializable;

/**
 * Created by rui on 2018/8/19.
 */
public class SensorVO implements Serializable{
    private static final long serialVersionUID = -7939196527142319354L;

    private long sensorId;

    private double crruentTemp;

    private String sensorName;

    private String state;

    public SensorVO(long sensorId, double crruentTemp, String sensorName,String state) {
        this.sensorId = sensorId;
        this.crruentTemp = crruentTemp;
        this.sensorName = sensorName;
        this.state = state;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public double getCrruentTemp() {
        return crruentTemp;
    }

    public void setCrruentTemp(double crruentTemp) {
        this.crruentTemp = crruentTemp;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
