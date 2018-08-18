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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String areaName;

    private long parentid;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid")
    private List<Area> sonAreas;


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

    public List<Area> getSonAreas() {
        return sonAreas;
    }

    public void setSonAreas(List<Area> sonAreas) {
        this.sonAreas = sonAreas;
    }

    public long getParentid() {
        return parentid;
    }

    public void setParentid(long parentid) {
        this.parentid = parentid;
    }
}
