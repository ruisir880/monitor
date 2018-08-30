create table terminal_info(
  id bigint not null auto_increment,
  terminal_id VARCHAR(50)not null,
  monitor_point_id bigint,
  gen_time datetime default CURRENT_TIMESTAMP,
  primary key (id)
);
alter table terminal_info add constraint fk_terminal_info foreign key (monitor_point_id)  references monitor_point (id);