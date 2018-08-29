    create table user_info (
        uid bigint not null auto_increment,
        username varchar(50) not null,
        password varchar(100) not null,
        real_name varchar(50) not null,
        mobile varchar(255) not null,
        email varchar(255),
        generation_time datetime default CURRENT_TIMESTAMP,
        address varchar(255),
        salt varchar(255) not null,
        state tinyint,
        PRIMARY KEY (UID)
    );
    ALTER TABLE user_info add constraint UQ_USERNAME unique (username);
    
   create table role_info (
        id bigint not null auto_increment,
        role_name varchar(50) not null,
        description varchar(255),
        primary key (id)
    );
    alter table role_info add constraint uq_rolename unique (role_name);
      
  
    create table privilege_info (
        id bigint not null auto_increment,
        privilege_name varchar(50) not null,
        description varchar(255),
        primary key (id)
    );
    alter table privilege_info add constraint uq_privilegename unique (privilege_name);
    
    
    create table user_role_map(
      user_id bigint not null,
      role_id bigint not null
    );
    alter table user_role_map add constraint uq_user_role unique (user_id,role_id);
    alter table user_role_map add constraint fk_user_role_u foreign key (user_id)  references user_info (uid);
    ALTER TABLE user_role_map add constraint FK_user_role_R foreign key (role_id)  references role_info (id);
    
    
    
    create table role_privilege_map(
      role_id bigint not null,
      privilege_id bigint not null
    );
    alter table role_privilege_map add constraint uq_role_privilege unique (role_id,privilege_id);
    alter table role_privilege_map add constraint fk_role_privilege_r foreign key (role_id)  references role_info (id);
    ALTER TABLE role_privilege_map add constraint fk_role_privilege_p foreign key (privilege_id)  references privilege_info (id);
	
	
	
--业务数据表
	
	
create table area(
  id bigint not null,
  area_name varchar(50) not null,
  parentid bigint,
  primary key (id)
);

create table user_area_map(
  user_id bigint,
  area_id bigint
);
alter table user_area_map add constraint uq_user_area unique (user_id,area_id);
alter table user_area_map add constraint fk_user_area_u foreign key (user_id)  references user_info (uid);
alter table user_area_map add constraint fk_user_area_a foreign key (area_id)  references area (id);

create table monitor_point(
  id bigint not null auto_increment,
  name varchar(50) not null,
  gen_time datetime default CURRENT_TIMESTAMP,
  client_company varchar(100),
  area_id bigint,
  address VARCHAR (255),
  primary key (id)
);
alter table monitor_point add constraint fk_monitor_point foreign key (area_id)  references area (id);


/*
create table user_monitor_map(
  user_id bigint,
  monitor_point_id bigint
);
alter table user_monitor_map add constraint uq_user_monitor unique (user_id,monitor_point_id);
alter table user_monitor_map add constraint fk_user_monitor_u foreign key (user_id)  references user_info (uid);
alter table user_monitor_map add constraint fk_user_monitor_m foreign key (monitor_point_id)  references monitor_point (id);
*/


create table sensor_info(
  id bigint not null auto_increment,
  sensor_id VARCHAR(50)not null,
  monitor_point_id bigint,
  gen_time datetime default CURRENT_TIMESTAMP,
  primary key (id)
);
alter table sensor_info add constraint fk_sensor_info foreign key (monitor_point_id)  references monitor_point (id);
alter table sensor_info add column  threshold_value double(6,2) default 30 ;

create table comment_info(
  id bigint not null auto_increment,
  message varchar(255),
  sensor_id bigint,
  primary key (id)
);
alter table comment_info add constraint fk_comment_info foreign key (sensor_id)  references sensor_info (id);

create table temp_info(
  id bigint not null auto_increment,
  sensor_id bigint,
  gen_time datetime default CURRENT_TIMESTAMP,
  temperature  double(6,2),
  state varchar(20),
  primary key (id)
);
alter table temp_info add constraint fk_temp_info foreign key (sensor_id)  references sensor_info (id);

