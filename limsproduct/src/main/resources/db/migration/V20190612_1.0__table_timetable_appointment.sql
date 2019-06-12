-- 功能：修改虚拟镜像字段类型
-- 作者：杨新蔚
-- 日期：2019-06-12
alter table timetable_appointment modify column virtual_image_id varchar(255) comment "课程所用的虚拟镜像id";