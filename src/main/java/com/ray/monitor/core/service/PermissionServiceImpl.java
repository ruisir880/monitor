package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.PrivilegeRepository;
import com.ray.monitor.core.repository.RoleRepository;
import com.ray.monitor.model.PrivilegeInfo;
import com.ray.monitor.model.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ray Rui on 9/3/2018.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<PrivilegeInfo> findAll() {
        return (List<PrivilegeInfo>) privilegeRepository.findAll();
    }

    public List<RoleInfo> findAllRoles(){
        return (List<RoleInfo>) roleRepository.findAll();
    }


    public RoleInfo findRole(Long roleId){
        return roleRepository.findById(roleId);
    }

    @Override
    public List<PrivilegeInfo> findPrivilegeById(List<Long> idList) {
        return (List<PrivilegeInfo>) privilegeRepository.findAll(idList);
    }

    @Override
    public void saveRole(RoleInfo roleInfo) {
        roleRepository.save(roleInfo);
    }
}
