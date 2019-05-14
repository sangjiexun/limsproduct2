-- 修改人数为人次数=人数*课次数
-- 计划内/计划外实验室使用统计/数据写入
-- 陈乐为
DROP PROCEDURE if exists `proc_report_out_plan_lab_rate`;
DROP PROCEDURE if exists `proc_report_in_plan_lab_rate`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_report_out_plan_lab_rate`()
BEGIN
	DELETE FROM report_plan_lab_rate;
	
	INSERT INTO report_plan_lab_rate
	#实验室预约中，除上课的其他用途-审核通过
	SELECT
	ta.term AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	lres.lending_time,
	st.term_name,
	'预约使用' AS usage_matt,
	1 AS user_num,
	tasn.end_class-tasn.start_class+1 AS course_hour_num,
	tasn.end_class-tasn.start_class+1 AS user_hour_num,
	1 AS course_num,
	cdi.c_name AS use_obj,
	2 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM timetable_appointment ta
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN timetable_lab_related tlr ON tlr.appointment_id=ta.id
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN lab_reservation lres ON lres.timetable_id=ta.id
	LEFT JOIN c_dictionary cdi ON cdi.id=lres.lending_user_type
	LEFT JOIN school_term st ON st.id=ta.term
	WHERE ta.timetable_style=7 AND lres.reservation_type!='RESERVATION_CLASS'

	UNION
	#设备预约-审核通过
	SELECT
	st.id AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	lrdr.begintime,
	st.term_name,
	'设备预约使用' AS usage_matt,
	1 AS user_num,
	#45分钟一课时
	(lrdr.reserve_hours*60)/45 AS course_hour_num,
	(lrdr.reserve_hours*60)/45 AS user_hour_num,
	1 AS course_num,
	`user`.cname,
	2 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM lab_room_device_reservation lrdr
	JOIN lab_room_device lrd ON lrd.id=lrdr.lab_room_device
	JOIN lab_room lr ON lr.id=lrd.lab_room_id
	JOIN school_term st ON st.id=lrdr.school_term
	JOIN `user` ON `user`.username=lrdr.reserve_user
	JOIN c_dictionary cd ON cd.id=lrdr.`status`
	WHERE cd.c_category='c_audit_result' AND cd.c_number=2

	UNION
	#校外参观登记
	SELECT
	st.id AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	vr.date,
	st.term_name,
	'校外参观登记' AS usage_matt,
	vr.visitor_number AS user_num,
	vr.course_hours AS course_hour_num,
	(vr.visitor_number*vr.course_hours) AS user_hour_num,
	1 AS course_num,
	vr.visiting_unit AS use_obj,
	2 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM visiting_registration vr
	JOIN lab_room lr ON lr.lab_room_name=vr.labroom_visiting
	JOIN school_term st ON vr.date BETWEEN st.term_start AND st.term_end
	WHERE vr.flag=1

	UNION
	#校内参观登记
	SELECT
	st.id AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	vr.date,
	st.term_name,
	'校内参观登记' AS usage_matt,
	vr.visitor_number AS user_num,
	vr.course_hours AS course_hour_num,
	(vr.visitor_number*vr.course_hours) AS user_hour_num,
	1 AS course_num,
	vr.visiting_unit AS use_obj,
	2 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM visiting_registration vr
	JOIN lab_room lr ON lr.lab_room_name=vr.labroom_visiting
	JOIN school_term st ON vr.date BETWEEN st.term_start AND st.term_end
	WHERE vr.flag=2

	UNION
	#工位预约
	SELECT
	st.id AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	lrsr.reservation_time,
	st.term_name,
	'工位预约' AS usage_matt,
	lrsr.station_count AS use_num,#预约工位数
	(lrsr.end_time-lrsr.start_time)*60/45/10000 AS per_course_hour,#工位数*时长
	lrsr.station_count*((lrsr.end_time-lrsr.start_time)*60/45/10000) AS per_stu_hour,#预约时长，一小时=一课时
	1 AS course_num,
	lrsr.station_count AS use_obj,
	2 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM lab_room_station_reservation lrsr
	JOIN lab_room lr ON lr.id=lrsr.lab
	JOIN school_term st ON st.id=lrsr.term_id;

	CALL proc_report_in_plan_lab_rate;

END;;
DELIMITER ;;

CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_report_in_plan_lab_rate`()
BEGIN
	INSERT INTO report_plan_lab_rate(term_id,term_name,lab_id,lab_name,user_num,course_num,course_hour_num,user_hour_num,flag,base_id,center_id)
	#教务不分批
	SELECT
	ta.term,
	st.term_name,
	lr.id,
	lr.lab_room_name,
	SUM((SELECT COUNT(DISTINCT student_number) FROM school_course_student WHERE course_detail_no=ta.detail_id)*(tasn.end_week-tasn.start_week+1)) AS '上课人数',
	SUM((tasn.end_week-tasn.start_week+1)) AS '课次数',
	SUM(((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '课时数',
	SUM(((SELECT COUNT(DISTINCT student_number) FROM school_course_student WHERE course_detail_no=ta.detail_id)*(tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '人时数',
	1 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM timetable_lab_related tlr
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN timetable_appointment ta ON ta.id=tlr.appointment_id
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN school_term st ON st.id=ta.term
	WHERE ta.`status`=1 AND ta.timetable_style<>4 AND ta.timetable_style<>5 AND ta.timetable_style<>6 AND ta.timetable_style<>7
	GROUP BY lr.id,ta.term

	UNION
	#教务&自主分批
	SELECT
	ta.term,
	st.term_name,
	lr.id,
	lr.lab_room_name,
	SUM((SELECT COUNT(DISTINCT username) FROM timetable_group_students WHERE group_id=tag.group_id)*(tasn.end_week-tasn.start_week+1)) AS '上课人数',
	SUM((tasn.end_week-tasn.start_week+1)) AS '课次数',
	SUM(((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '课时数',
	SUM((SELECT COUNT(DISTINCT username) FROM timetable_group_students WHERE group_id=tag.group_id)*((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '人时数',
	1 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM timetable_lab_related tlr
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN timetable_appointment ta ON ta.id=tlr.appointment_id
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN timetable_appointment_group tag ON tag.appointment_id=ta.id
	JOIN school_term st ON st.id=ta.term
	WHERE ta.`status`=1
	GROUP BY lr.id,ta.term

	UNION
	#自主不分批
	SELECT
	ta.term,
	st.term_name,
	lr.id,
	lr.lab_room_name,
	SUM((SELECT COUNT(DISTINCT student_number) FROM timetable_course_student WHERE course_code_id=ta.self_course_code)*(tasn.end_week-tasn.start_week+1)) AS '上课人数',
	SUM((tasn.end_week-tasn.start_week+1)) AS '课次数',
	SUM(((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '课时数',
	SUM((SELECT COUNT(DISTINCT student_number) FROM timetable_course_student WHERE course_code_id=ta.self_course_code)*((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '人时数',
	1 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM timetable_lab_related tlr
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN timetable_appointment ta ON ta.id=tlr.appointment_id
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN school_term st ON st.id=ta.term
	WHERE ta.`status`=1 AND ta.timetable_style=5
	GROUP BY lr.id,ta.term

	UNION
	#实验室预约-上课人数不定
	SELECT
	ta.term,
	st.term_name,
	lr.id,
	lr.lab_room_name,
	SUM((tasn.end_week-tasn.start_week+1)) AS '上课人数',
	SUM((tasn.end_week-tasn.start_week+1)) AS '课次数',
	SUM(((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '课时数',
	SUM(((tasn.end_week-tasn.start_week+1)*(tasn.end_class-tasn.start_class+1))) AS '人时数',
	1 AS flag,
	lr.lab_base_id,
	lr.lab_center_id
	FROM timetable_lab_related tlr
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN timetable_appointment ta ON ta.id=tlr.appointment_id
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN lab_reservation lres ON lres.timetable_id=ta.id AND lres.reservation_type='RESERVATION_CLASS'
	JOIN school_term st ON st.id=ta.term
	WHERE ta.`status`=1 AND ta.timetable_style=7
	GROUP BY lr.id,ta.term
;

END;;

DELIMITER ;;