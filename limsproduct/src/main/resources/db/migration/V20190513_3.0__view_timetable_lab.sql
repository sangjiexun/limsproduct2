-- 实验教学计划表视图，view_timetable_lab
-- 刘博越
drop view if exists `view_timetable_lab`;
CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `view_timetable_lab`AS 
select `ta`.`id` AS `appId`,group_concat(`lr`.`lab_room_name` separator '<br>') AS `labId`,`lc`.`center_name` AS `center_name` from ((((`timetable_appointment` `ta` left join `timetable_lab_related` `tlr` on((`tlr`.`appointment_id` = `ta`.`id`))) left join `lab_room` `lr` on((`lr`.`id` = `tlr`.`lab_id`))) left join `lab_annex` `la` on((`la`.`id` = `lr`.`lab_annex_id`))) left join `lab_center` `lc` on((`lc`.`id` = `la`.`belong_center`))) where (1 = 1) group by `ta`.`id` ;

