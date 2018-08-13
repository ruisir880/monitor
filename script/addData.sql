insert into `sys_permission`(name,parent_id,parent_ids,available,permission,resource_type,url)
VALUES ('USER_MANAGEMENT',0,'0/' ,1,'userInfo:view', 'menu', 'userInfo/userList');  
  
insert into `sys_permission`(name,parent_id,parent_ids,available,permission,resource_type,url)
VALUES ('DEVICE_MANAGEMENT',1,'0/1',1,'userInfo:add', 'button', 'userInfo/userAdd');  
  
INSERT INTO `sys_role`(available,description,role) VALUES (1,'管理员','admin');  
INSERT INTO `sys_role`(available,description,role) VALUES (1,'技术人员','tech');  
  
INSERT INTO `sys_role_permission`(permission_id,role_id) VALUES ('1', '1');  
INSERT INTO `sys_role_permission`(permission_id,role_id) VALUES ('2', '1');  
  
INSERT INTO `user_info`(username,real_name,password,salt,state,mobile) VALUES ('admin','Ray', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', '0','123456');
  
insert into `sys_user_role`(uid,role_id) values (1,1);  
INSERT INTO `sys_user_role`(uid,role_id) VALUES (1,2);  