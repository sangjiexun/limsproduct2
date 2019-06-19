-- 功能：修改课表虚拟镜像推送表添加镜像名称字段
-- 作者：杨新蔚
-- 日期：2019-06-19
alter table virtual_image_schedule add column virtual_image_name varchar(255) comment "镜像名称" after course_no;