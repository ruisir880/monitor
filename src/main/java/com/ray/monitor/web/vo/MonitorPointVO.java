package com.ray.monitor.web.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public class MonitorPointVO implements Serializable{

    private static final long serialVersionUID = 919736275210121213L;

    private String monitorPointName;

    private List<TerminalVO> terminalVOList;

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
