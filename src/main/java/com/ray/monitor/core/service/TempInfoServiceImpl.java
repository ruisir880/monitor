package com.ray.monitor.core.service;

import com.ray.monitor.core.Constants;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.TempInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
@Service
public class TempInfoServiceImpl implements TempInfoService {
    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public List<TempInfo> findBySensorIds(List<Long> sensorIdList) {
        return tempRepository.findBySensorId(sensorIdList);
    }


    @Override
    public List<TempInfo> findTempByCondition(long monitorPointId, Date startDate, Date endDate) {
        return tempRepository.findTempByCondition(monitorPointId, startDate, endDate);
    }


    //需要保证startDate和endDate不为空
    public Page<TempInfo> pageUserQuery(long monitorPointId,int page ,Date startDate,Date endDate,String state){

        //分页信息
        Pageable pageable = new PageRequest(page-1, Constants.PAGE_SIZE); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return tempRepository.findByPage(monitorPointId, startDate, endDate, pageable);
    }
}
