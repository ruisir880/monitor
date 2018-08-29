package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.AreaRepository;
import com.ray.monitor.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ray Rui on 8/29/2018.
 */
@Service
public class AreaServcieImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


    @Override
    public Area findById(long areaId) {
        return areaRepository.findOne(areaId);
    }
}
