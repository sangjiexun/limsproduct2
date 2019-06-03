-- 虚拟镜像表添加可用字段
-- 杨新蔚 2019-6-3
alter table virtual_image add enable  int(1) default 1  COMMENT '虚拟镜像是否可用：0不可用，1可用';