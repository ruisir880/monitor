package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class SensorInfo implements Serializable {

    private static final long serialVersionUID = 8423544213209615300L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long sensorId;

    private Date genTime;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name="monitor_point_id")//设置在employee表中的关联字段(外键)
    private MonitorPoint monitorPoint;

    @OneToMany(mappedBy="sensorInfo",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<CommentInfo> commentInfoList;

    @OneToMany(mappedBy="sensorInfo",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<TempInfo> tempInfoList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
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

    public Set<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(Set<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }

    public Set<TempInfo> getTempInfoList() {
        return tempInfoList;
    }

    public void setTempInfoList(Set<TempInfo> tempInfoList) {
        this.tempInfoList = tempInfoList;
    }
}