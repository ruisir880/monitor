package com.ray.monitor.core.service;

import com.ray.monitor.core.constant.Constants;
import com.ray.monitor.core.constant.TempState;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    public List<TempInfo> findTempByCondition(long monitorPointId, Date startDate, Date endDate,String ... terminalId) {
        if(terminalId == null || terminalId.length == 0 || StringUtils.isEmpty(terminalId[0])) {
            return tempRepository.findTempByCondition(monitorPointId, startDate, endDate);
        }else {
            return tempRepository.findTempByTerminalId(Long.parseLong(terminalId[0]), startDate, endDate);
        }
    }


    //需要保证startDate和endDate不为空
    public Page<TempInfo> pageQuery(long monitorPointId, int page , Date startTime, Date endTime, TempState state, String... terminalId){

        //分页信息
        String startDate = DateUtil.formatDate(startTime);
        String endDate = DateUtil.formatDate(endTime);
        Pageable pageable = new PageRequest(page-1, Constants.PAGE_SIZE); //页码：前端从1开始，jpa从0开始，做个转换
        Specification<TempInfo> specification = new Specification<TempInfo>() {
            @Override
            public Predicate toPredicate(Root<TempInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(state != null){
                    predicates.add(cb.equal(root.get("state"), state.toString()));
                }
                if(terminalId == null || terminalId.length == 0 || StringUtils.isEmpty(terminalId[0])){
                    predicates.add(cb.equal(root.get("sensorInfo").get("terminalInfo").get("monitorPoint").get("id"),monitorPointId));
                }else {
                    predicates.add(cb.equal(root.get("sensorInfo").get("terminalInfo").get("id"),Long.parseLong(terminalId[0])));
                }
                predicates.add(cb.greaterThanOrEqualTo(root.get("genTime").as(String.class), startDate));
                predicates.add(cb.lessThanOrEqualTo(root.get("genTime").as(String.class), endDate));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        return this.tempRepository.findAll(specification,pageable);
    }

    @Override
    public void deleteData(long monitorPointId, int page, Date startDate, Date endDate, TempState state, String... terminalId) {

    }
}
