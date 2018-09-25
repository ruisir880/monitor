package com.ray.monitor.core.service;

import com.ray.monitor.core.constant.TempState;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.web.vo.SensorVO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public interface TempInfoService {

    List<TempInfo> findBySensorIds(List<Long> sensorIdList);

    List<SensorVO> findByTerminalId(List<Long> terminalIds);

    List<TempInfo> findTempByCondition(long monitorPointId,Date startDate,Date endDate,String ... terminalId);

    Page<TempInfo> pageQuery(long monitorPointId, int page , Date startDate, Date endDate, TempState state, String... terminalId);

    int deleteData(long monitorPointId, Date startDate, Date endDate, TempState state, String... terminalId);
}
