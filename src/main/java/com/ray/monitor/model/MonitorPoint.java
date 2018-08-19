package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by rui on 2018/8/18.
 */
@Entity
public class MonitorPoint implements Serializable {
    private static final long serialVersionUID = -8938436958022564333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String clientCompany;

    private Date genTime;

    private long areaId;

    @OneToMany(mappedBy="monitorPoint",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<SensorInfo> sensorInfoList;

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

    public String getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public Set<SensorInfo> getSensorInfoList() {
        return sensorInfoList;
    }

    public void setSensorInfoList(Set<SensorInfo> sensorInfoList) {
        this.sensorInfoList = sensorInfoList;
    }
}
