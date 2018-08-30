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

    private String terminalName;

    private double threshold;

    private List<TempHistoryVO> tempHistoryVOList;


    public SensorVO(long sensorId, String sensorName,String terminalName,double threshold) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.terminalName = terminalName;
        this.threshold = threshold;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

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

    public List<TempHistoryVO> getTempHistoryVOList() {
        return tempHistoryVOList;
    }

    public void setTempHistoryVOList(List<TempHistoryVO> tempHistoryVOList) {
        this.tempHistoryVOList = tempHistoryVOList;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
