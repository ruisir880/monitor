package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import com.ray.monitor.core.Constants;
import com.ray.monitor.core.repository.AreaRepository;
import com.ray.monitor.core.repository.CommentRepository;
import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.PageQueryRepository;
import com.ray.monitor.core.repository.RoleRepository;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.CommentInfo;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.RoleInfo;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private TempInfoService tempInfoService;

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
        System.out.println("========================" + sensor.getCommentInfoList().size());

        Set<SensorInfo> sensorInfos = sensorRepository.findSensorInfoByMonitorIdWithTemp(1L);
        System.out.println("========================"+sensorInfos.size());
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
        System.out.println("========================" + userInfos.getTotalPages());
        System.out.println("========================" + userInfos.getSize());
        System.out.println("========================" + userInfos.getNumberOfElements());
    }

    @Test
    public void test_PageQuery2(){
        Page<UserInfo> userInfos  = userInfoService.pageUserQuery(1, null, null, null);
        System.out.println("========================"+userInfos.getTotalPages());
        System.out.println("========================"+userInfos.getSize());
        System.out.println("========================"+userInfos.getNumberOfElements());
    }

    @Test
    public void test_Temp() throws Exception {
        List<Long> longs = Arrays.asList(1L, 2L, 3L);
        List<TempInfo> tempInfoList = tempRepository.findBySensorId(longs);
        System.out.println("========================"+tempInfoList.size());

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.set(2018, 7, 1);
        calendar2.set(2018, 7, 20);
        List<TempInfo> tempInfoSet = tempRepository.findTempByCondition(1L,calendar.getTime(),calendar2.getTime());
        System.out.println("========================"+tempInfoSet.size());
    }

    @Test
    public void test_PageQueryTemp() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000,1,1);
        Date  startDate = calendar.getTime();
        Pageable pageable = new PageRequest(0, Constants.PAGE_SIZE); //页码：前端从1开始，jpa从0开始，做个转换

        Page<TempInfo> tempInfoSet  = tempRepository.findByPage(1L,startDate,new Date(),pageable);
        System.out.println("========================"+tempInfoSet.getTotalElements());
    }

}