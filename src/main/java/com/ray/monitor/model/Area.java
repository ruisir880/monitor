package com.ray.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
@Entity
public class Area implements Serializable {

    @Id
    private long id;

    private String areaName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid",referencedColumnName = "id")
    private Area parentArea;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    public Area getParentArea() {
        return parentArea;
    }

    public void setParentArea(Area parentArea) {
        this.parentArea = parentArea;
    }
}
