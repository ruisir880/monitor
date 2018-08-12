INSERT INTO `sys_permission`(name,parent_id,parent_ids,available,permission,resource_type,url)   
VALUES ('用户管理',0,'0/' ,1,'userInfo:view', 'menu', 'userInfo/userList');  
  
INSERT INTO `sys_permission`(name,parent_id,parent_ids,available,permission,resource_type,url)   
VALUES ('用户添加',1,'0/1',1,'userInfo:add', 'button', 'userInfo/userAdd');  
  
INSERT INTO `sys_permission`(name,parent_id,parent_ids,available,permission,resource_type,url)   
VALUES ('用户删除',1,'0/1',1,'userInfo:del', 'button', 'userInfo/userDel');  
  
INSERT INTO `sys_role`(available,description,role) VALUES (1,'管理员','admin');  
INSERT INTO `sys_role`(available,description,role) VALUES (1,'VIP会员','vip');  
  
INSERT INTO `sys_role_permission`(permission_id,role_id) VALUES ('1', '1');  
INSERT INTO `sys_role_permission`(permission_id,role_id) VALUES ('2', '1');  
  
INSERT INTO `user_info`(name,password,salt,state,username) VALUES ('管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', '0', 'admin');  
  
INSERT INTO `sys_user_role`(uid,role_id) VALUES (1,1);  
INSERT INTO `sys_user_role`(uid,role_id) VALUES (1,2);  