package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class TemperatureVO implements Serializable {
    private static final long serialVersionUID = 3563268282672572173L;

    private String sensorName;

    private String terminalName;

    private double currentTemp;

    private String currentTempTime;

    private String state;

    private double threshold;

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCurrentTempTime() {
        return currentTempTime;
    }

    public void setCurrentTempTime(String currentTempTime) {
        this.currentTempTime = currentTempTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
