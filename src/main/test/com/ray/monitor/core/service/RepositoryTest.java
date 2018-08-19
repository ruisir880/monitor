package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import com.ray.monitor.core.repository.*;
import com.ray.monitor.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.management.Sensor;

import java.util.Arrays;
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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PageQueryRepository<UserInfo, Long> pageQueryRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TempRepository tempRepository;

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
        System.out.println("========================"+sensor.getCommentInfoList().size());
    }

    @Test
    public void test_AddComment() throws Exception {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setMessage("test add message");
        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setId(1L);
        commentInfo.setSensorInfo(sensorInfo);
        commentRepository.save(commentInfo);
    }

    @Test
    public void test_PageQuery() throws Exception {
        Page<UserInfo> userInfos = pageQueryRepository.findAll(new PageRequest(0,10));
        System.out.println("========================"+userInfos.getTotalPages());
        System.out.println("========================"+userInfos.getSize());
        System.out.println("========================"+userInfos.getNumberOfElements());
    }

    @Test
    public void test_PageQuery2(){
        Page<UserInfo> userInfos  = userInfoService.pageUserQuery(1,null,null,null);
        System.out.println("========================"+userInfos.getTotalPages());
        System.out.println("========================"+userInfos.getSize());
        System.out.println("========================"+userInfos.getNumberOfElements());
    }

    @Test
    public void test_Temp() throws Exception {
        List<Long> longs = Arrays.asList(1L,2L,3L);
        List<TempInfo> tempInfoList = tempRepository.findBySensorId(longs);
        System.out.println("========================"+tempInfoList.size());
    }

}