-- 功能：lab_room表新增字段实验室下的工位预约是否需要审核
-- 作者：贺照易
-- 日期：2019-06-03
alter table `lab_room` add `is_station_audit` int(11) COMMENT '工位预约是否需要审核' AFTER `end_hour_inweekend`;
ALTER TABLE `lab_room` ADD CONSTRAINT `lab_room_ibfk_19` FOREIGN KEY (`is_station_audit`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;