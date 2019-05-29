-- 虚拟镜像表去外键
-- 杨新蔚 2019-5-29
alter table virtual_image_reservation drop foreign key virtual_image_reservation_1; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_2; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_3; 
alter table virtual_image_reservation drop foreign key virtual_image_reservation_4; 