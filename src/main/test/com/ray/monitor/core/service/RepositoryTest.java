package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import com.ray.monitor.core.repository.AreaRepository;
import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.RoleRepository;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.RoleInfo;
import com.ray.monitor.model.SensorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.management.Sensor;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class RepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void test1() throws Exception {
        List<RoleInfo> roles  = (List<RoleInfo>) roleRepository.findAll();
        System.out.println("========================"+roles.size());
    }

    @Test
    public void test_area() throws Exception {
        Area area  =  areaRepository.findOne(110000L);
        System.out.println("========================"+area.getSonAreas().size());
    }

    @Test
    public void test_monitorPoint() throws Exception {
        MonitorPoint monitorPoint  =  monitorRepository.findOne(1L);
        System.out.println("========================"+monitorPoint.getName());

        List<MonitorPoint> monitorPoints  =  monitorRepository.findByAreaId(310300L);
        System.out.println("========================"+monitorPoints.size());
    }

    @Test
    public void test_sensor() throws Exception {
        SensorInfo sensor  =  sensorRepository.findOne(1L);
        System.out.println("========================"+sensor.getGenTime());
        System.out.println("========================"+sensor.getMonitorPoint().getName());
    }

}