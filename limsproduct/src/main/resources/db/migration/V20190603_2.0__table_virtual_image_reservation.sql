-- 虚拟镜像预约表修改字段类型
-- 杨新蔚 2019-6-3
alter table virtual_image_reservation add virtual_image_name  varchar(255) NULL COMMENT '虚拟镜像名称' after virtual_image;
-- 虚拟镜像预约表添加特殊账号字段
-- 杨新蔚 2019-6-3
alter table virtual_image_reservation add image_account  varchar(255) NULL COMMENT '虚拟镜像账号' after virtual_image_name;
-- 虚拟镜像预约表添加是否下载ica字段
-- 杨新蔚 2019-6-3
alter table virtual_image_reservation add is_download_ica  int(1) default 0 COMMENT '是否下载ica';