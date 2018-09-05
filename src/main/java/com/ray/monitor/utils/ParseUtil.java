package com.ray.monitor.utils;

import com.ray.monitor.model.*;
import com.ray.monitor.web.vo.*;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rui on 2018/8/19.
 */
public class ParseUtil {

    public static final String HTML_DATE_PARTTERN = "yyyy-MM-dd hh:mm";

    public static final String DATETIME_PARTTERN = "yyyyMMddhhmmss";


    public static TempVO getTempInto( List<TempInfo> tempInfoList){
        Map<Long,SensorVO> tempInfoMap = new LinkedHashMap<>();
        TempVO tempVO = new TempVO();

        for(TempInfo tempInfo : tempInfoList){
            List<TempHistoryVO> tempHistoryVOs;
            if(!tempInfoMap.containsKey(tempInfo.getSensorInfo().getId())) {
                tempVO.setMonitorPointName(tempInfo.getSensorInfo().getTerminalInfo().getMonitorPoint().getName());
                SensorVO sensorVO = new SensorVO(
                        tempInfo.getSensorInfo().getId(),
                        tempInfo.getSensorInfo().getSensorId(),
                        tempInfo.getSensorInfo().getTerminalInfo().getTerminalId(),
                        tempInfo.getSensorInfo().getThresholdValue());
                tempInfoMap.put(tempInfo.getSensorInfo().getId(), sensorVO);
                tempHistoryVOs = new ArrayList<>();
                sensorVO.setTempHistoryVOList(tempHistoryVOs);
            }else {
                tempHistoryVOs = tempInfoMap.get(tempInfo.getSensorInfo().getId()).getTempHistoryVOList();
            }
            tempHistoryVOs.add(new TempHistoryVO(
                    tempInfo.getTemperature(),
                    DateUtil.formatDate(tempInfo.getGenTime()),
                    tempInfo.getState()));
        }

        tempVO.setSensorVOList(new ArrayList<SensorVO>(tempInfoMap.values()));
        return tempVO;
    }

    public static Date parseDate(String htmlTime) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat(HTML_DATE_PARTTERN);
        return sdf.parse(  htmlTime.replace("T"," "));
    }



    public static PageTempVO getPageTempVO(Page<TempInfo> tempInfoPage){
        PageTempVO pageTempVO = new PageTempVO();
        pageTempVO.setTotalCount(tempInfoPage.getTotalElements());
        pageTempVO.setTotalPage(tempInfoPage.getTotalPages());
        pageTempVO.setPage(tempInfoPage.getNumber());
        pageTempVO.setTempVO( getTempInto(tempInfoPage.getContent()));
        return pageTempVO;

    }


    public static List<AreaVO> getAreaVOS(List<Area> areaList){
        List<AreaVO> areaVOS = new ArrayList<>();
        for(Area elem: areaList){
            areaVOS.add(new AreaVO(elem.getId(),elem.getAreaName()));
        }
        return areaVOS;
    }

    public static List<TerminalVO> getTerminalVOS(Collection<TerminalInfo> terminalInfoList){
        List<TerminalVO> terminalVOList = new ArrayList<>();

        TerminalVO tempVO = null;
        for(TerminalInfo terminalInfo : terminalInfoList){
            tempVO = new TerminalVO(
                    terminalInfo.getId(),
                    terminalInfo.getTerminalId(),
                    DateUtil.formatDate(terminalInfo.getGenTime()));

            tempVO.setSensorVOList(getSensorVOList(terminalInfo.getSensorInfoList()));
            terminalVOList.add(tempVO);
        }
         Collections.sort(terminalVOList, (o1, o2) -> o1.getId()> o2.getId() ? 1:-1);
        return terminalVOList;
    }


    public static CurrentTempVO getCurrentTempVO(List<TempInfo> tempInfoList ){
        CurrentTempVO vo = new CurrentTempVO();
        if(tempInfoList == null || tempInfoList.size() == 0){
            return vo;
        }

        List<TemperatureVO> temperatureVOList = new ArrayList<>();
        vo.setTemperatureVOList(temperatureVOList);
        vo.setMonitorPointName(tempInfoList.get(0).getSensorInfo().getTerminalInfo().getMonitorPoint().getName());
        for(TempInfo tempInfo: tempInfoList){
            TemperatureVO temperatureVO = new TemperatureVO();
            temperatureVO.setSensorName(tempInfo.getSensorInfo().getSensorId());
            temperatureVO.setTerminalName(tempInfo.getSensorInfo().getTerminalInfo().getTerminalId());
            temperatureVO.setCurrentTemp(tempInfo.getTemperature());
            temperatureVO.setCurrentTempTime(DateUtil.formatDate(tempInfo.getGenTime()));
            temperatureVO.setThreshold(tempInfo.getSensorInfo().getThresholdValue());
            temperatureVOList.add(temperatureVO);
        }
        return vo;
    }

    public static List<MonitorSensorVO> getMonitorSensorVOList(List<MonitorPoint> monitorPointList){
        List<MonitorSensorVO> voList = new ArrayList<>();
        for(MonitorPoint monitorPoint : monitorPointList){
            voList.add(getMonitorSensorVO(monitorPoint));
        }
        return voList;
    }

    public static MonitorSensorVO getMonitorSensorVO(MonitorPoint monitorPoint ){
        MonitorSensorVO vo = new MonitorSensorVO();
        if(monitorPoint == null){
            return vo;
        }
        vo.setId(monitorPoint.getId());
        vo.setMonitorPointName(monitorPoint.getName());
        vo.setAddress(monitorPoint.getAddress());
        vo.setClientCompany(monitorPoint.getClientCompany());
        vo.setGenTime(DateUtil.formatDate(monitorPoint.getGenTime()));
        Area area = monitorPoint.getArea();
        vo.setArea(area.getParentArea().getAreaName() + " "+area.getAreaName());
        if(monitorPoint.getTerminalInfoList() == null || monitorPoint.getTerminalInfoList().size() == 0){
            return vo;
        }

        List<TerminalVO> terminalVOList = new ArrayList<>();
        vo.setTerminalVOList(terminalVOList);
        for(TerminalInfo terminalInfo : monitorPoint.getTerminalInfoList()){

        terminalVOList.add(
            new TerminalVO(terminalInfo.getId(),
            terminalInfo.getTerminalId(),
            DateUtil.formatDate(terminalInfo.getGenTime())));
        }
        terminalVOList.sort(Comparator.comparing(TerminalVO::getGenTime));
        return vo;
    }


    public static List<PrivilegeVO> getPrivilegeVOS(List<PrivilegeInfo> privilegeInfos){
        List<PrivilegeVO> voList = new ArrayList<>();
        for(PrivilegeInfo info : privilegeInfos){
            voList.add(new PrivilegeVO(info.getId(),info.getDescription()));
        }
        return voList;
    }

    public static List<RoleVO> getRoleVOS(List<RoleInfo> roleInfos){
        List<RoleVO> voList = new ArrayList<>();
        for(RoleInfo info : roleInfos){
            voList.add(new RoleVO(info.getId(),info.getDescription()));
        }
        return voList;
    }

    public static TerminalVO getTerminalVO(Collection<SensorInfo> list){
        TerminalVO vo = new TerminalVO();
        if (list.size() > 0) {
            TerminalInfo info = list.iterator().next().getTerminalInfo();
            vo.setId(info.getId());
            vo.setName(info.getTerminalId());
            vo.setGenTime(DateUtil.formatDate(info.getGenTime()));
        }
        vo.setSensorVOList(getSensorVOList(list));
        return vo;
    }

    public static List<SensorVO> getSensorVOList(Collection<SensorInfo> list){
        List<SensorVO> sensorVOList = new ArrayList<>();
        for(SensorInfo info : list){
            sensorVOList.add(new SensorVO(info.getId(),info.getSensorId(),info.getTerminalInfo().getTerminalId(),info.getThresholdValue()));
        }
        Collections.sort(sensorVOList);

        return sensorVOList;
    }
}
