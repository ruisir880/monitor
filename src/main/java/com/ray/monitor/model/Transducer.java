package com.ray.monitor.model;

import java.io.Serializable;

public class Transducer implements Serializable {

    private long id;
    private String deviceName;
    private double temperature;

    public Transducer(long id, String deviceName, double temperature) {
        this.id = id;
        this.deviceName = deviceName;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return deviceName ;
    }
}