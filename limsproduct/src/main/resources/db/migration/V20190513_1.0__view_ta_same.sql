-- 实验教学计划表视图，ta_same
-- 刘博越
drop view if exists `view_ta_same`;
CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `view_ta_same`AS 
select `ta`.`id` AS `appId`,'school' AS `style`,count(`tas`.`id`) AS `same_numbers`,`sc`.`term_id` AS `termId` from ((`timetable_appointment` `ta` left join `timetable_appointment_same_number` `tas` on((`tas`.`appointment_id` = `ta`.`id`))) left join `school_course` `sc` on((`sc`.`course_no` = `ta`.`course_no`))) where (`ta`.`timetable_style` not in (5,6)) group by `ta`.`id` union select `ta`.`id` AS `appId`,'self' AS `style`,count(`tas`.`id`) AS `same_numbers`,`tsc`.`term_id` AS `termId` from ((`timetable_appointment` `ta` left join `timetable_appointment_same_number` `tas` on((`tas`.`appointment_id` = `ta`.`id`))) left join `timetable_self_course` `tsc` on((`tsc`.`id` = `ta`.`self_course_code`))) where (`ta`.`timetable_style` in (5,6)) group by `ta`.`id` ;
