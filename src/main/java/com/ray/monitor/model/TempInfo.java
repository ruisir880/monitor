package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by rui on 2018/8/18.
 */
@Entity
public class TempInfo implements Serializable{

    private static final long serialVersionUID = -8568648096688753884L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name="sensor_id")//设置在employee表中的关联字段(外键)
    private SensorInfo sensorInfo;

    private Date genTime;

    private double temperature;

    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SensorInfo getSensorInfo() {
        return sensorInfo;
    }

    public void setSensorInfo(SensorInfo sensorInfo) {
        this.sensorInfo = sensorInfo;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
