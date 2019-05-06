-- 修改timetable_appointment_time表的外键
-- 黄保钱

ALTER TABLE `timetable_appointment_time` DROP FOREIGN KEY `timetable_appointment_time_ibfk_1`;

ALTER TABLE `timetable_appointment_time`
ADD CONSTRAINT `timetable_appointment_time_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `timetable_appointment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;