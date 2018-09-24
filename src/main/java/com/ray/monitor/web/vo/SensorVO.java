package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public class SensorVO implements Serializable,Comparable{
    private static final long serialVersionUID = -7939196527142319354L;

    private long sensorId;

    private String sensorName;

    private String terminalName;

    private double threshold;

    private double currentTemp;

    private List<TempHistoryVO> tempHistoryVOList;


    public SensorVO(long sensorId, String sensorName,String terminalName,double threshold) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.terminalName = terminalName;
        this.threshold = threshold;
    }

    public SensorVO(long sensorId, String sensorName,String terminalName,double threshold,double currentTemp) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.terminalName = terminalName;
        this.threshold = threshold;
        this.currentTemp = currentTemp;
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

    @Override
    public int compareTo(Object o) {
        if(o instanceof SensorVO){
            return this.getSensorName().compareTo(((SensorVO)o).getSensorName());
        }
        return 0;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }
}
