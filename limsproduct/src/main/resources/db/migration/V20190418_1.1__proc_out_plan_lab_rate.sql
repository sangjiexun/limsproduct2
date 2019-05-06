-- 修改-清空并推送数据到计划外实验室使用统计表
-- 陈乐为 2019年4月18日
DROP PROCEDURE if exists `proc_report_out_plan_lab_rate`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_report_out_plan_lab_rate`()
BEGIN
	DELETE FROM report_out_plan_lab_rate;
	
	INSERT INTO report_out_plan_lab_rate
	#实验室预约中，除上课的其他用途
	SELECT
	ta.term AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	lres.lending_time,
	st.term_name,
	'预约使用' AS usage_matt,
	1 AS use_num,
	tasn.end_class-tasn.start_class+1 AS per_course_hour,
	tasn.end_class-tasn.start_class+1 AS per_stu_hour,
	cdi.c_name AS use_obj,
	cd.c_name AS use_app,
	2 AS flag
	FROM timetable_appointment ta
	JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
	JOIN timetable_lab_related tlr ON tlr.appointment_id=ta.id
	JOIN lab_room lr ON lr.id=tlr.lab_id
	JOIN lab_reservation lres ON lres.timetable_id=ta.id
	JOIN c_dictionary cd ON cd.id=lres.lending_type
	LEFT JOIN c_dictionary cdi ON cdi.id=lres.lending_user_type
	LEFT JOIN school_term st ON st.id=ta.term
	WHERE ta.timetable_style=7 AND (cd.c_category='lab_room_lending_type' AND cd.c_number<>1)

	UNION
	#设备预约
	SELECT
	st.id AS term_id,
	lr.id AS lab_id,
	lr.lab_room_name,
	lr.lab_room_address,
	lrdr.begintime,
	st.term_name,
	'设备预约使用' AS usage_matt,
	1 AS use_num,
	lrdr.reserve_hours AS per_course_hour,
	lrdr.reserve_hours AS per_stu_hour,
	`user`.cname,
	lrdr.content,
	2 AS flag
	FROM lab_room_device_reservation lrdr 
	JOIN lab_room_device lrd ON lrd.id=lrdr.lab_room_device
	JOIN lab_room lr ON lr.id=lrd.lab_room_id
	JOIN school_term st ON st.id=lrdr.school_term
	JOIN `user` ON `user`.username=lrdr.reserve_user

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
	vr.visitor_number AS use_num,
	(vr.visitor_number*vr.course_hours) AS per_course_hour,
	vr.course_hours AS per_stu_hour,
	vr.visiting_unit AS use_obj,
	'校外参观' AS use_app,
	2 AS flag
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
	vr.visitor_number AS use_num,
	(vr.visitor_number*vr.course_hours) AS per_course_hour,
	vr.course_hours AS per_stu_hour,
	vr.visiting_unit AS use_obj,
	'校内参观' AS use_app,
	2 AS flag
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
	lrsr.station_count*((lrsr.end_time-lrsr.start_time)/10000) AS per_course_hour,#工位数*时长
	(lrsr.end_time-lrsr.start_time)/10000 AS per_stu_hour,#预约时长，一小时=一课时
	lrsr.station_count AS use_obj,
	'工位预约' AS use_app,
	2 AS flag
	FROM lab_room_station_reservation lrsr
	JOIN lab_room lr ON lr.id=lrsr.lab
	JOIN school_term st ON st.id=lrsr.term_id;

END;;

DELIMITER ;;