package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class MonitorSensorVO implements Serializable {
    private static final long serialVersionUID = 2326851459488688471L;

    private long id;

    private String monitorPointName;

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
}
