package com.ray.monitor.core.service;

import com.ray.monitor.model.PrivilegeInfo;
import com.ray.monitor.model.RoleInfo;

import java.util.List;

/**
 * Created by Ray Rui on 9/3/2018.
 */
public interface PermissionService {

    List<PrivilegeInfo> findAll();

    List<RoleInfo> findAllRoles();

    RoleInfo findRole(Long roleId);

    List<PrivilegeInfo> findPrivilegeById(List<Long> idList);

    void saveRole(RoleInfo roleInfo);
}
