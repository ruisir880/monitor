create table monitor_point_email ( mp_id bigint not null , emailList varchar(1000));
ALTER TABLE monitor_point_email add constraint UQ_monitor_point_email unique (mp_id);