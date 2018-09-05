package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Ray Rui on 8/30/2018.
 */
@Entity
public class TerminalInfo implements Serializable {
    private static final long serialVersionUID = -2570048739796159543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String terminalId;

    private Date genTime;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="monitor_point_id")
    private MonitorPoint monitorPoint;

    @OneToMany(mappedBy="terminalInfo",cascade=CascadeType.ALL,orphanRemoval = true,fetch=FetchType.LAZY)
    private Set<SensorInfo> sensorInfoList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public MonitorPoint getMonitorPoint() {
        return monitorPoint;
    }

    public void setMonitorPoint(MonitorPoint monitorPoint) {
        this.monitorPoint = monitorPoint;
    }

    public Set<SensorInfo> getSensorInfoList() {
        return sensorInfoList;
    }

    public void setSensorInfoList(Set<SensorInfo> sensorInfoList) {
        this.sensorInfoList = sensorInfoList;
    }
}
