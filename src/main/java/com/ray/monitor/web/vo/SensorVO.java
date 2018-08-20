package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public class SensorVO implements Serializable{
    private static final long serialVersionUID = -7939196527142319354L;

    private long sensorId;

    private String sensorName;

    private double crruentTemp;

    private Date currentTempTime;

    private String state;

    private List<TempHistoryVO> tempHistoryVOList;

    public SensorVO(long sensorId, double crruentTemp, String sensorName,String state) {
        this.sensorId = sensorId;
        this.crruentTemp = crruentTemp;
        this.sensorName = sensorName;
        this.state = state;
    }


    public SensorVO(long sensorId, String sensorName) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
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

    public Date getCurrentTempTime() {
        return currentTempTime;
    }

    public void setCurrentTempTime(Date currentTempTime) {
        this.currentTempTime = currentTempTime;
    }

    public List<TempHistoryVO> getTempHistoryVOList() {
        return tempHistoryVOList;
    }

    public void setTempHistoryVOList(List<TempHistoryVO> tempHistoryVOList) {
        this.tempHistoryVOList = tempHistoryVOList;
    }
}
