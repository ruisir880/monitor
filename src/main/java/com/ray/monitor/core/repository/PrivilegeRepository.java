package com.ray.monitor.core.repository;

import com.ray.monitor.model.PrivilegeInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rui on 2018/8/18.
 */
public interface PrivilegeRepository extends CrudRepository<PrivilegeInfo, Long> {
}
