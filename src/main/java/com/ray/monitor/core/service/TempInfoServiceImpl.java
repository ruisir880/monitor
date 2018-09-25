package com.ray.monitor.core.service;

import com.ray.monitor.core.constant.Constants;
import com.ray.monitor.core.constant.TempState;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.utils.DateUtil;
import com.ray.monitor.web.vo.SensorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
    private EntityManager entityManager;

    @Override
    public List<SensorVO> findByTerminalId(List<Long> terminalIds) {
        List<Object[]>  objectList =tempRepository.findByTerminalId(terminalIds);
        List<SensorVO> sensorVOs = new ArrayList<>();
        for(Object[] elem : objectList){
            sensorVOs.add(new SensorVO(Long.valueOf(elem[0].toString()),
                    elem[1].toString(),
                    elem[2].toString(),
                    elem[3] == null ? 0.0 : (Double) elem[3],
                    elem[4] == null ? 0.0 : (Double) elem[4]));
        }
        return sensorVOs;
    }

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
        Pageable pageable = new PageRequest(page-1, Constants.PAGE_SIZE); //页码：前端从1开始，jpa从0开始，做个转换
        Specification<TempInfo> tempInfoSpecification = getSpecification(monitorPointId,startTime,endTime,state,terminalId);
        return this.tempRepository.findAll(tempInfoSpecification,pageable);
    }

    /*@Override
    @Transactional
    public int deleteData(long monitorPointId, Date startTime, Date endTime, TempState state, String... terminalId) {
        StringBuilder sqlBuilder =new StringBuilder(" select t from temp_info t left join sensor_info sf left join terminal_info tf" +
                " where tf.monitor_point_id =:mpId and gen_time between :startDate and :endDate");
        if(state != null){
            sqlBuilder.append(" and t.state=:state");
        }
        boolean terminalCondition = (terminalId == null || terminalId.length == 0 || StringUtils.isEmpty(terminalId[0]));
        if(!terminalCondition){
            sqlBuilder.append(" and tf.id =:terminalId");
        }

        Query query = entityManager.createNativeQuery(sqlBuilder.toString())
                .setParameter("mpId",monitorPointId)
                .setParameter("startDate",startTime)
                .setParameter("endDate",endTime);
        if(state != null){
            query.setParameter("state",state);
        }
        if(!terminalCondition){
            query.setParameter("terminalId",terminalId);
        }

        List result = query.getResultList();
        tempRepository.delete(result);
        return result.size();
    }
*/


    private Specification<TempInfo> getSpecification(long monitorPointId, Date startTime, Date endTime, TempState state, String... terminalId){
        String startDate = DateUtil.formatDate(startTime);
        String endDate = DateUtil.formatDate(endTime);
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

                query.orderBy(cb.asc(root.get("genTime")));
                query.orderBy(cb.asc(root.get("sensorInfo").get("sensorId")));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        return specification;
    }
    @Override
    @Transactional
    public int deleteData(long monitorPointId, Date startDate, Date endDate, TempState state, String... terminalId) {
        StringBuilder sqlBuilder =new StringBuilder(
                "delete temp_info from temp_info  left join sensor_info  on temp_info.sensor_id = sensor_info.id  " +
                        "left join terminal_info  on sensor_info.terminal_id=terminal_info.id  " +
                        "where terminal_info.monitor_point_id =:mpId ");
        if(state != null){
            sqlBuilder.append(" and temp_info.state=:state");
        }
        boolean terminalCondition = (terminalId == null || terminalId.length == 0 || StringUtils.isEmpty(terminalId[0]));
        if(!terminalCondition){
            sqlBuilder.append(" and terminal_info.id =:terminalId");
        }

        Query query = entityManager.createNativeQuery(sqlBuilder.toString())
                .setParameter("mpId",monitorPointId);
        if(state != null){
            query.setParameter("state",state.toString());
        }
        if(!terminalCondition){
            query.setParameter("terminalId",Long.valueOf(terminalId[0]));
        }
        return query.executeUpdate();
    }
}
