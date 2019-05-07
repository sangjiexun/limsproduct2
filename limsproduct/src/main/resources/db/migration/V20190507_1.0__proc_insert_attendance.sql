-- 写入并更新timetable_attendance数据
-- 陈乐为 2019年5月7日
DROP PROCEDURE if exists `proc_insert_attendance`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_insert_attendance`(IN `term_id` int)
BEGIN

	DELETE FROM timetable_attendance WHERE timetable_attendance.term_id=term_id AND timetable_attendance.actual_attendance=0 AND timetable_attendance.attendance_machine=0;

	INSERT INTO timetable_attendance(user_number,begin_time,end_time,start_class,end_class,weekday,`week`,appointment_id,style,term_id,course_no,created_date,attend_date)

	SELECT tab_1.user_number,tab_1.begin_time,tab_1.end_time,tab_1.start_class,tab_1.end_class,tab_1.weekday,tab_1.`week`,
					tab_1.appointment_id,tab_1.style,tab_1.term_id,tab_1.course_no,tab_1.created_date,tab_1.date FROM(

		SELECT
		scs.student_number AS user_number,
		CONCAT(sw.date," ",st_start.start_date) AS begin_time,
		CONCAT(sw.date," ",st_end.end_date) AS end_time,
		tasn.start_class AS start_class,
		tasn.end_class AS end_class,
		sw.weekday AS weekday,
		sw.`week` AS `week`,
		'' AS labroom_id,
		ta.id AS appointment_id,
		ta.timetable_style AS style,
		ta.term AS term_id,
		sc.course_no AS course_no,
		CURRENT_TIMESTAMP AS created_date,
		sw.date
		FROM timetable_appointment ta
		JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
		JOIN school_week sw ON sw.term_id=ta.term AND sw.weekday=ta.weekday AND sw.`week` BETWEEN tasn.start_week AND tasn.end_week
		JOIN system_time st_start ON st_start.section = tasn.start_class
		JOIN system_time st_end ON st_end.section = tasn.end_class
		JOIN school_course sc ON sc.course_no=ta.course_no
		JOIN school_course_detail scd ON scd.course_detail_no=ta.detail_id
		JOIN school_course_student scs ON scs.course_detail_no=scd.course_detail_no

		WHERE ta.`status`=1 AND ta.term=term_id AND ta.timetable_style<4

		UNION

		SELECT
		tgs.username AS user_number,
		CONCAT(sw.date," ",st_start.start_date) AS begin_time,
		CONCAT(sw.date," ",st_end.end_date) AS end_time,
		tasn.start_class AS start_class,
		tasn.end_class AS end_class,
		sw.weekday AS weekday,
		sw.`week` AS `week`,
		'' AS labroom_id,
		ta.id AS appointment_id,
		ta.timetable_style AS style,
		ta.term AS term_id,
		sc.course_no AS course_no,
		CURRENT_TIMESTAMP AS created_date,
		sw.date
		FROM timetable_appointment ta
		JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
		JOIN school_week sw ON sw.term_id=ta.term AND sw.weekday=ta.weekday AND sw.`week` BETWEEN tasn.start_week AND tasn.end_week
		JOIN system_time st_start ON st_start.section = tasn.start_class
		JOIN system_time st_end ON st_end.section = tasn.end_class
		JOIN school_course sc ON sc.course_no=ta.course_no
		JOIN timetable_appointment_group tag ON tag.appointment_id=ta.id
		JOIN timetable_group_students tgs ON tgs.group_id=tag.group_id

		WHERE ta.`status`=1 AND ta.term=term_id AND (ta.timetable_style=4 OR ta.timetable_style=6)

		UNION

		SELECT
		tcs.student_number AS user_number,
		CONCAT(sw.date," ",st_start.start_date) AS begin_time,
		CONCAT(sw.date," ",st_end.end_date) AS end_time,
		tasn.start_class AS start_class,
		tasn.end_class AS end_class,
		sw.weekday AS weekday,
		sw.`week` AS `week`,
		'' AS labroom_id,
		ta.id AS appointment_id,
		ta.timetable_style AS style,
		ta.term AS term_id,
		tsc.course_number AS course_no,
		CURRENT_TIMESTAMP AS created_date,
		sw.date
		FROM timetable_appointment ta
		JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
		JOIN school_week sw ON sw.term_id=ta.term AND sw.weekday=ta.weekday AND sw.`week` BETWEEN tasn.start_week AND tasn.end_week
		JOIN system_time st_start ON st_start.section = tasn.start_class
		JOIN system_time st_end ON st_end.section = tasn.end_class
		JOIN timetable_self_course tsc ON tsc.id=ta.self_course_code
		JOIN timetable_course_student tcs ON tcs.course_code_id=tsc.id

		WHERE ta.`status`=1 AND ta.term=term_id AND ta.timetable_style=5

		UNION

		SELECT
		tgs.username AS user_number,
		CONCAT(sw.date," ",st_start.start_date) AS begin_time,
		CONCAT(sw.date," ",st_end.end_date) AS end_time,
		tasn.start_class AS start_class,
		tasn.end_class AS end_class,
		sw.weekday AS weekday,
		sw.`week` AS `week`,
		'' AS labroom_id,
		ta.id AS appointment_id,
		ta.timetable_style AS style,
		ta.term AS term_id,
		tsc.course_number AS course_no,
		CURRENT_TIMESTAMP AS created_date,
		sw.date
		FROM timetable_appointment ta
		JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
		JOIN school_week sw ON sw.term_id=ta.term AND sw.weekday=ta.weekday AND sw.`week` BETWEEN tasn.start_week AND tasn.end_week
		JOIN system_time st_start ON st_start.section = tasn.start_class
		JOIN system_time st_end ON st_end.section = tasn.end_class
		JOIN timetable_self_course tsc ON tsc.id=ta.self_course_code
		JOIN timetable_appointment_group tag ON tag.appointment_id=ta.id
		JOIN timetable_group_students tgs ON tgs.group_id=tag.group_id

		WHERE ta.`status`=1 AND ta.term=term_id AND (ta.timetable_style=4 OR ta.timetable_style=6)
	) AS tab_1
	LEFT JOIN timetable_attendance attendance ON tab_1.user_number=attendance.user_number
		AND tab_1.begin_time=attendance.begin_time AND tab_1.end_time=attendance.end_time
	WHERE attendance.id IS NULL;

UPDATE limsproduct.timetable_attendance
JOIN (
	SELECT
		lvu.username AS username,
		ich.cardno AS cardno,
		ich.datetime AS datetime,
		lra.lab_room_id AS lab_room_id,
		tlr.appointment_id AS appointment_id
	FROM
		limsproduct.view_user lvu
	JOIN iot.common_hdwlog ich ON lvu.cardnumber = ich.cardno
	JOIN lab_room_agent lra ON ich.hardware_ip = lra.hardware_ip AND lra.hardware_type=547
	JOIN timetable_lab_related tlr ON lra.lab_room_id = tlr.lab_id
) AS ltb ON limsproduct.timetable_attendance.appointment_id = ltb.appointment_id
AND limsproduct.timetable_attendance.user_number = ltb.username
SET limsproduct.timetable_attendance.attendance_machine = 1
WHERE
	ltb.datetime BETWEEN DATE_SUB(
		limsproduct.timetable_attendance.begin_time,
		INTERVAL 10 MINUTE
	)
AND DATE_ADD(
	limsproduct.timetable_attendance.end_time,
	INTERVAL 10 MINUTE
);

UPDATE limsproduct.timetable_attendance
JOIN (
	SELECT
		lvu.username AS username,
		ich.cardno AS cardno,
		ich.datetime AS datetime,
		lra.lab_room_id AS lab_room_id,
		tlr.appointment_id AS appointment_id
	FROM
		limsproduct.view_user lvu
	JOIN iot.common_hdwlog ich ON lvu.cardnumber = ich.cardno
	JOIN lab_room_agent lra ON ich.hardware_ip = lra.hardware_ip AND lra.hardware_type=547
	JOIN timetable_lab_related tlr ON lra.lab_room_id = tlr.lab_id
) AS ltb ON limsproduct.timetable_attendance.appointment_id = ltb.appointment_id
AND limsproduct.timetable_attendance.user_number = ltb.username
SET limsproduct.timetable_attendance.machine_time = ltb.datetime
WHERE
	limsproduct.timetable_attendance.attendance_machine = 1;

END;;
DELIMITER ;