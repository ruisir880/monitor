package com.ray.monitor.utils;

import com.ray.monitor.core.Constants;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
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

    public static TempVO setTempIntoSensor(Set<SensorInfo> sensorInfoList, List<TempInfo> tempInfoList){
        Map<Long,TempInfo> tempInfoMap = new HashMap<>();
        List<SensorVO> sensorVOlist = new ArrayList<>();
        TempVO tempVO = new TempVO();

        for(TempInfo tempInfo : tempInfoList){
            tempInfoMap.put(tempInfo.getSensorInfo().getId(),tempInfo);
        }
        for(SensorInfo sensor : sensorInfoList){
            TempInfo temp = tempInfoMap.get(sensor.getId());
            tempVO.setMonitorPointName(sensor.getMonitorPoint().getName());
            if(temp!=null){
                sensorVOlist.add(new SensorVO(sensor.getId(),temp.getTemperature(),sensor.getSensorId(),temp.getState()));
            }else {
                sensorVOlist.add(new SensorVO(sensor.getId(),0,sensor.getSensorId(), Constants.TEMP_STATE_NORMAL));
            }

        }
        tempVO.setSensorVOList(sensorVOlist);
        Collections.sort(sensorVOlist, new Comparator<SensorVO>() {
            @Override
            public int compare(SensorVO o1, SensorVO o2) {
                return o1.getSensorName().compareTo(o2.getSensorName());
            }

        });
        return tempVO;
    }

    public static TempVO getTempInto( List<TempInfo> tempInfoList){
        Map<Long,SensorVO> tempInfoMap = new LinkedHashMap<>();
        TempVO tempVO = new TempVO();

        for(TempInfo tempInfo : tempInfoList){
            List<TempHistoryVO> tempHistoryVOs;
            if(!tempInfoMap.containsKey(tempInfo.getSensorInfo().getId())) {
                tempVO.setMonitorPointName(tempInfo.getSensorInfo().getMonitorPoint().getName());
                SensorVO sensorVO = new SensorVO(tempInfo.getSensorInfo().getId(),tempInfo.getSensorInfo().getSensorId());
                tempInfoMap.put(tempInfo.getSensorInfo().getId(), sensorVO);
                tempHistoryVOs = new ArrayList<>();
                sensorVO.setTempHistoryVOList(tempHistoryVOs);
            }else {
                tempHistoryVOs = tempInfoMap.get(tempInfo.getSensorInfo().getId()).getTempHistoryVOList();
            }
            tempHistoryVOs.add(new TempHistoryVO(tempInfo.getTemperature(),tempInfo.getGenTime(),tempInfo.getState()));
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

}
