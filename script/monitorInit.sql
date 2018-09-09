insert into privilege_info (privilege_name,description) values('privilege.management','权限管理');
insert into privilege_info (privilege_name,description) values('user.list','用户查询');
insert into privilege_info (privilege_name,description) values('user.edit','用户编辑');
insert into privilege_info (privilege_name,description) values('monitorPoint.list','监测点查询');
insert into privilege_info (privilege_name,description) values('monitorPoint.edit','监测点编辑');
insert into privilege_info (privilege_name,description) values('terminal.list','终端查询');
insert into privilege_info (privilege_name,description) values('terminal.edit','终端编辑');
insert into privilege_info (privilege_name,description) values('sensor.list','传感器查询');
insert into privilege_info (privilege_name,description) values('sensor.edit','传感器编辑');
insert into privilege_info (privilege_name,description) values('sensorThreshold.edit','传感器阈值设置');
insert into privilege_info (privilege_name,description) values('tempInfo.List','温度信息查询');
insert into privilege_info (privilege_name,description) values('tempInfo.edit','温度信息编辑');
insert into privilege_info (privilege_name,description) values('siteMap.query','地图查看');
insert into privilege_info (privilege_name,description) values('siteMap.edit','地图监测点定位');

insert into user_info(username,password,real_name,mobile,address,salt) value ('admin','4da85196d96808f7d69e5ac6f5354cda','管理者','123456789','管理者地址','f319cd5b392d16c68e3abd8af3fde536');

insert into role_info(role_name,description) values('admin','管理员');
insert into role_info(role_name,description) values('projectManager','项目负责人');
insert into role_info(role_name,description) values('cooperation','协作单位');
insert into role_info(role_name,description) values('technician','技术专家');

insert into user_role_map values(1,1);

insert into role_privilege_map(role_id,privilege_id) values(1,1);
