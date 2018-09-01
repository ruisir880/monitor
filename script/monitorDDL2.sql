create table terminal_info(
  id bigint not null auto_increment,
  terminal_id VARCHAR(50)not null,
  monitor_point_id bigint,
  gen_time datetime default CURRENT_TIMESTAMP,
  primary key (id)
);
alter table terminal_info add constraint fk_terminal_info foreign key (monitor_point_id)  references monitor_point (id);
ALTER TABLE terminal_info add constraint uq_terminal unique (terminal_id,monitor_point_id);

alter table sensor_info drop foreign key fk_sensor_info;
alter table sensor_info add terminal_id bigint ;
alter table sensor_info add constraint fk_sensor_terminal foreign key (terminal_id)  references terminal_info (id);
alter table sensor_info drop column monitor_point_id;

alter table monitor_point add constraint uq_monitor_point unique(name,area_id);