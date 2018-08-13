
  create table sys_permission (
      ID BIGINT NOT NULL AUTO_INCREMENT,
      name varchar(255) NOT NUll,
      parent_id bigint,
      parent_ids varchar(255),
      permission varchar(255),
      resource_type enum('menu','button'),
      url varchar(255),
      available BIT NOT NUll,
      PRIMARY KEY (ID)
  );

  ALTER TABLE sys_permission ADD  CONSTRAINT UQ_NAME  UNIQUE(name);

    create table sys_role (
        id bigint not null auto_increment,
        available bit,
        description varchar(255),
        role varchar(255) not null,
        PRIMARY KEY (ID)
    );
    ALTER TABLE sys_role ADD  CONSTRAINT UQ_ROLE  UNIQUE(role);

    create table sys_role_permission (
        role_id bigint not null,
        permission_id BIGINT NOT NULL
    );
    
    create table sys_user_role (
      uid BIGINT NOT NULL,
      role_id bigint not null

    );

    create table user_info (
        uid bigint not null auto_increment,
        username varchar(255) not null,
        password varchar(255) not null,
        real_name varchar(255) not null,
        email varchar(255),
        mobile varchar(255) not null,
        salt varchar(255) not null,
        state tinyint not null,
        generation_time datetime,
        PRIMARY KEY (UID)
    );

    ALTER TABLE user_info add constraint UQ_USERNAME unique (username);

    ALTER TABLE sys_role_permission 
        add constraint FK_RP_PERMISSION
        FOREIGN KEY (PERMISSION_ID) 
        references sys_permission (id);

    ALTER TABLE sys_role_permission 
        add constraint FK_RP_ROLE 
        FOREIGN KEY (ROLE_ID) 
        references sys_role (id);

    ALTER TABLE sys_user_role 
        add constraint FK_UR_USERINFO 
        FOREIGN KEY (UID) 
        references user_info (uid);

    ALTER TABLE sys_user_role 
        add constraint FK_UR_ROLE 
        FOREIGN KEY (ROLE_ID) 
        references sys_role (id);