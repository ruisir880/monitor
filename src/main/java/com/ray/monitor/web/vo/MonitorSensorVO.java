package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class MonitorSensorVO implements Serializable {
    private static final long serialVersionUID = 2326851459488688471L;

    private long id;

    private String monitorPointName;

    private String clientCompany;

    private String genTime;

    private String address;

    private String area;

    private Double latitude;

    private Double dimension;


    private List<TerminalVO> terminalVOList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonitorPointName() {
        return monitorPointName;
    }

    public void setMonitorPointName(String monitorPointName) {
        this.monitorPointName = monitorPointName;
    }

    public List<TerminalVO> getTerminalVOList() {
        return terminalVOList;
    }

    public void setTerminalVOList(List<TerminalVO> terminalVOList) {
        this.terminalVOList = terminalVOList;
    }


    public String getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDimension() {
        return dimension;
    }

    public void setDimension(Double dimension) {
        this.dimension = dimension;
    }
}
