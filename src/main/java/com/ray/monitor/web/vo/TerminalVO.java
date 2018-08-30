package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public class TerminalVO implements Serializable{

    private static final long serialVersionUID = -1388657357077787189L;

    private long id;

    private String name;

    private String genTime;

    private List<SensorVO> sensorVOList;

    public TerminalVO(long id, String name, String genTime) {
        this.id = id;
        this.name = name;
        this.genTime = genTime;
    }

    public TerminalVO(long id, String name, String genTime, List<SensorVO> sensorVOList) {
        this.id = id;
        this.name = name;
        this.genTime = genTime;
        this.sensorVOList = sensorVOList;
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

    public List<SensorVO> getSensorVOList() {
        return sensorVOList;
    }

    public void setSensorVOList(List<SensorVO> sensorVOList) {
        this.sensorVOList = sensorVOList;
    }
}
