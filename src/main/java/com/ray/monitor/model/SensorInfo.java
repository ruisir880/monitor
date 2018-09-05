package com.ray.monitor.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class SensorInfo implements Serializable {

    private static final long serialVersionUID = 8423544213209615300L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String sensorId;

    private Date genTime;

    private double thresholdValue;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name="terminal_id")//设置在employee表中的关联字段(外键)
    private TerminalInfo terminalInfo;

    @OneToMany(mappedBy="sensorInfo",cascade=CascadeType.ALL,orphanRemoval = true,fetch=FetchType.EAGER)
    private Set<CommentInfo> commentInfoList;

    @OneToMany(mappedBy="sensorInfo",cascade=CascadeType.ALL,orphanRemoval = true,fetch=FetchType.LAZY)
    private Set<TempInfo> tempInfoList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
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

    public double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public TerminalInfo getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }
}