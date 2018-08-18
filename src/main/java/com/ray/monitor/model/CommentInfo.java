package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rui on 2018/8/18.
 */
@Entity
public class CommentInfo implements Serializable {
    private static final long serialVersionUID = -1672532656300945294L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name="sensor_id")//设置在employee表中的关联字段(外键)
    private SensorInfo sensorInfo;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
