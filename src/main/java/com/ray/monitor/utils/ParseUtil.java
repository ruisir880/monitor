package com.ray.monitor.utils;

import com.ray.monitor.core.Constants;
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

    public static String formateDate(Date date){
        SimpleDateFormat sdf= new SimpleDateFormat(DATETIME_PARTTERN);
        return sdf.format(date);
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

    public static List<TerminalVO> getTerminalVOS(List<TerminalInfo> terminalInfoList){
        List<TerminalVO> terminalVOList = new ArrayList<>();

        for(TerminalInfo terminalInfo : terminalInfoList){
            terminalVOList.add(new TerminalVO(
                    terminalInfo.getId(),
                    terminalInfo.getTerminalId(),
                    DateUtil.formatDate(terminalInfo.getGenTime())));
        }
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

    public static MonitorSensorVO getMonitorSensorVO(MonitorPoint monitorPoint ){
        MonitorSensorVO vo = new MonitorSensorVO();
        if(monitorPoint == null){
            return vo;
        }
        vo.setMonitorPointName(monitorPoint.getName());
        if(monitorPoint.getTerminalInfoList() == null || monitorPoint.getTerminalInfoList().size() == 0){
            return vo;
        }

        List<TerminalVO> terminalVOList = new ArrayList<>();
        vo.setTerminalVOList(terminalVOList);
        for(TerminalInfo terminalInfo : monitorPoint.getTerminalInfoList()){
            List<SensorVO> sensorVOList = new ArrayList<>();
            for(SensorInfo sensorInfo : terminalInfo.getSensorInfoList()){
                sensorVOList.add(new SensorVO(sensorInfo.getId(),sensorInfo.getSensorId(),terminalInfo.getTerminalId(),sensorInfo.getThresholdValue()));
            }
            terminalVOList.add(new TerminalVO(terminalInfo.getId(),
                    terminalInfo.getTerminalId(),
                    DateUtil.formatDate(terminalInfo.getGenTime()),
                    sensorVOList));
        }
        return vo;
    }
}
