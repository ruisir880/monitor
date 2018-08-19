package com.ray.monitor.core.service;

import com.ray.monitor.model.TempInfo;

import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public interface TempInfoService {

    List<TempInfo> findBySensorIds(List<Long> sensorIdList);
}
