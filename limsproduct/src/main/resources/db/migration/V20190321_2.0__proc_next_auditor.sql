-- 删除重建
-- 陈乐为 2019-3-21
-- 重建
drop PROCEDURE if exists `proc_timetable_next_auditor`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_timetable_next_auditor`(IN `auth_name` varchar(40),IN `business_type` varchar(255),IN `business_uid` varchar(255))
BEGIN
	# 实验室管理员
	IF auth_name = 'LABMANAGER' THEN
		SELECT DISTINCT
			lra.username
			FROM timetable_appointment AS ta
			JOIN school_course AS sc ON sc.course_no=ta.course_no AND sc.course_no=business_uid
			JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id
		UNION
		SELECT DISTINCT
			lra.username
			FROM timetable_appointment AS ta
			JOIN timetable_self_course AS tsc ON tsc.id=ta.self_course_code AND tsc.id=business_uid
			JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id;
	END IF;
	# 其他权限-关联学院查询
	IF auth_name <> 'LABMANAGER' THEN
		CALL proc_academy_next_auditor(`auth_name`,`business_uid`);
	END IF;
END;;
DELIMITER ;