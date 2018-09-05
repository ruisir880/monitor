package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import com.ray.monitor.core.constant.Constants;
import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.repository.*;
import com.ray.monitor.model.*;
import com.ray.monitor.utils.DateUtil;
import com.ray.monitor.web.vo.MonitorSensorVO;
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
    private UserInfoRepository userInfoRepository;

    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private TempInfoService tempInfoService;

    @Autowired
    private SensorInfoService sensorInfoService;

    @Test
    public void test1() throws Exception {
        List<RoleInfo> roles  = (List<RoleInfo>) roleRepository.findAll();
        System.out.println("========================"+roles.size());
    }

    @Test
    public void test_area() throws Exception {
        Area area  =  areaRepository.findOne(110100L);
        System.out.println("========================"+area.getParentArea().getAreaName());
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
        SensorInfo sensor  =  sensorRepository.findOne(2L);
        System.out.println("========================"+sensor.getGenTime());
        System.out.println("========================"+sensor.getTerminalInfo().getMonitorPoint().getName());
        System.out.println("========================" + sensor.getCommentInfoList().size());

        Set<SensorInfo> sensorInfos = sensorRepository.findSensorInfoByMonitorIdWithTemp(1L);
        System.out.println("========================"+sensorInfos.size());
    }

    @Test
    public void test_AddComment() throws Exception {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setMessage("test save message");
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
    public void test_SaveThreshold() throws Exception {
        SensorInfo sensor  =  sensorRepository.findOne(1L);
        sensor.setThresholdValue(36.0);
        sensorRepository.save(sensor);
    }

    @Test
    public void test_DeleteSensro() throws Exception {
       // sensorInfoService.deleteSensor(1L,"7");
    }


    @Autowired
    private MonitorCache monitorCache;


    @Test
    public void test_QueryMonitor() throws Exception {
        List<MonitorPoint> monitorPoints =  monitorRepository.findAll();
        System.out.println("==============================="+monitorPoints.size());
    }

    @Autowired
    private TerminalRepository terminalRepository;
    @Test
    public void test_QueryTerminal() throws Exception {
        List<TerminalInfo> terminalInfos = (List<TerminalInfo>) terminalRepository.findAll();
        System.out.println("==============================="+terminalInfos.size());
    }

}