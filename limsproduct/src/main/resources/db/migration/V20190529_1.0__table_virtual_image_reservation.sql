-- 虚拟镜像预约表去外键
-- 杨新蔚 2019-5-29
alter table virtual_image_reservation drop foreign key virtual_image_reservation_1; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_2; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_3; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_4; 

-- 虚拟镜像预约表修改字段类型
-- 杨新蔚 2019-5-29
alter table virtual_image_reservation modify column virtual_image varchar(255);