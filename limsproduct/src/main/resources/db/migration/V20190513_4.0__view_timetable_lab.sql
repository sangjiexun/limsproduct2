-- 实验教学计划表视图，view_timetable_item
-- 刘博越
drop view if exists `view_timetable_item`;
CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `view_timetable_item`AS 
select `ta`.`id` AS `appId`,`tir`.`item_id` AS `item_id` from (`timetable_appointment` `ta` join `timetable_item_related` `tir` on((`tir`.`appointment_id` = `ta`.`id`))) where (1 = 1) ;
