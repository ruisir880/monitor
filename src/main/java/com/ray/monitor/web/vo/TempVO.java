package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public class TempVO implements Serializable{
    private static final long serialVersionUID = 865630214650445611L;

    private String monitorPointName;

    private List<SensorVO> sensorVOList;

    public String getMonitorPointName() {
        return monitorPointName;
    }

    public void setMonitorPointName(String monitorPointName) {
        this.monitorPointName = monitorPointName;
    }

    public List<SensorVO> getSensorVOList() {
        return sensorVOList;
    }

    public void setSensorVOList(List<SensorVO> sensorVOList) {
        this.sensorVOList = sensorVOList;
    }
}
