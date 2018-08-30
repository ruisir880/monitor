package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class CurrentTempVO implements Serializable {
    private static final long serialVersionUID = 865630214650445611L;

    private String monitorPointName;

    private List<TemperatureVO> temperatureVOList;

    public String getMonitorPointName() {
        return monitorPointName;
    }

    public void setMonitorPointName(String monitorPointName) {
        this.monitorPointName = monitorPointName;
    }

    public List<TemperatureVO> getTemperatureVOList() {
        return temperatureVOList;
    }

    public void setTemperatureVOList(List<TemperatureVO> temperatureVOList) {
        this.temperatureVOList = temperatureVOList;
    }
}
