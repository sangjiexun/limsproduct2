-- 实验教学计划表视图
-- 刘博越
drop view if exists `view_timetable_course_detail`;
CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `view_timetable_course_detail`AS 
select `a`.`course_number` AS `courseNumber`,`a`.`course_name` AS `courseName`,`a`.`course_no` AS `courseNo`,`a`.`course_class_name` AS `courseClass`,`a`.`experimental_class_hour` AS `experimentalHour`,count(distinct `e`.`lp_name`) AS `itemNumber`,'是' AS `property`,`a`.`plan_student_number` AS `planStudentnumber`,'本科' AS `studentType`,'公办' AS `class`,`f`.`cname` AS `teacher`,`f`.`major_direction` AS `majorDirection`,'要求大纲作为附件提供' AS `outlineName`,`a`.`course_name` AS `textbook`,`a`.`term_id` AS `termId`,`b`.`detail_id` AS `detail_id` from (((((`school_course_detail` `a` join `timetable_appointment` `b` on((`b`.`detail_id` = `a`.`course_detail_no`))) left join `user` `f` on((`a`.`teacher_number` = `f`.`username`))) left join `timetable_item_related` `c` on((`c`.`appointment_id` = `b`.`id`))) left join `timetable_lab_related` `d` on((`d`.`appointment_id` = `b`.`id`))) left join `operation_item` `e` on((`c`.`item_id` = `e`.`id`))) group by `b`.`detail_id` ;

