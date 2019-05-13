-- 实验教学计划表视图，view_timetable_full
-- 刘博越
drop view if exists `view_timetable_full`;
CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `view_timetable_full`AS 
select `vl`.`center_name` AS `center_name`,`vl`.`labId` AS `lab_name`,`vp`.`planTime` AS `planTime`,`oi`.`lp_name` AS `lp_name`,((substr(`vp`.`classes`,3,1) - substr(`vp`.`classes`,1,1)) + 1) AS `lp_department_hours`,`app`.`c_name` AS `app`,`req`.`c_name` AS `req`,`main`.`c_name` AS `main`,`scd`.`plan_student_number` AS `plan_student_number`,'1' AS `student_group`,'未变动' AS `status_change`,`sc`.`course_number` AS `course_number`,`vp`.`termId` AS `termId`,`ta`.`course_no` AS `course_no`,`vp`.`weekday` AS `weekday`,`vp`.`classes` AS `classes`,`ta`.`detail_id` AS `detail_id`,`vp`.`lptime` AS `lptime` from ((((((((((`timetable_appointment` `ta` left join `school_course` `sc` on((`sc`.`course_no` = `ta`.`course_no`))) join `school_course_detail` `scd` on((`scd`.`course_detail_no` = `ta`.`detail_id`))) join `view_timetable_lab` `vl` on((`vl`.`appId` = `ta`.`id`))) join `view_timetable_plan` `vp` on((`vp`.`appId` = `ta`.`id`))) join `view_timetable_item` `vi` on((`vi`.`appId` = `ta`.`id`))) left join `operation_item` `oi` on((`oi`.`id` = `vi`.`item_id`))) left join `c_dictionary` `app` on((`app`.`id` = `oi`.`lp_category_app`))) left join `c_dictionary` `req` on((`req`.`id` = `oi`.`lp_category_require`))) left join `c_dictionary` `main` on((`main`.`id` = `oi`.`lp_category_main`))) left join `c_dictionary` `status_change` on((`status_change`.`id` = `oi`.`lp_status_change`))) where (`oi`.`lp_name` is not null) order by `ta`.`start_week` ;
