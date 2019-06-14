-- 功能：修改课表虚拟镜像推送表添加课程字段
-- 作者：杨新蔚
-- 日期：2019-06-18
alter table virtual_image_schedule add column course_no varchar(255) comment "课程id" after appointment_id;