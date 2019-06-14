-- 功能：更新当日课表镜像数据至virtual_image_schedule表
-- 作者：杨新蔚
-- 日期：2019-06-14
DROP PROCEDURE if exists `proc_update_virtual_image_schedule`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_update_virtual_image_schedule`()
BEGIN

DECLARE outer_done INT DEFAULT 0;


DECLARE appointment_id_vis INT (11);


DECLARE course_no_vis VARCHAR (255);


DECLARE virtual_image_id_vis VARCHAR (255);


DECLARE term_vis INT (11);


DECLARE start_week_vis INT (11);


DECLARE end_week_vis INT (11);


DECLARE weekday_vis INT (11);


DECLARE start_class_vis INT (11);


DECLARE end_class_vis INT (11);


DECLARE update_date_vis datetime;


DECLARE start_time_vis datetime;


DECLARE end_time_vis datetime;


DECLARE student_count_vis INT (11);


DECLARE app_cursor CURSOR FOR SELECT
	join_table.id,
	join_table.course_no,
	join_table.virtual_image_id,
	join_table.term,
	timetable_appointment_same_number.start_week AS start_week,
	timetable_appointment_same_number.end_week AS end_week,
	join_table.weekday,
	timetable_appointment_same_number.start_class AS start_class,
	timetable_appointment_same_number.end_class AS end_class,
	CURDATE()
FROM
	timetable_appointment_same_number
LEFT JOIN (
	SELECT
		timetable_appointment.id AS id,
		timetable_appointment.course_no AS course_no,
		timetable_appointment.virtual_image_id AS virtual_image_id,
		timetable_appointment.weekday AS weekday,
		timetable_appointment.term AS term
	FROM
		timetable_appointment
	LEFT JOIN school_course ON timetable_appointment.course_no = school_course.course_no
	WHERE
		timetable_appointment.course_no IS NOT NULL
	AND virtual_image_id IS NOT NULL
	AND LENGTH(TRIM(virtual_image_id)) <> 0
) join_table ON timetable_appointment_same_number.appointment_id = join_table.id
WHERE
	join_table.course_no IS NOT NULL
AND timetable_appointment_same_number.start_week <= (
	SELECT
		school_week.`week`
	FROM
		school_week
	WHERE
		school_week.date = CURDATE()
)
AND timetable_appointment_same_number.end_week >= (
	SELECT
		school_week.`week`
	FROM
		school_week
	WHERE
		school_week.date = CURDATE()
)
AND join_table.weekday = (
	SELECT
		school_week.`weekday`
	FROM
		school_week
	WHERE
		school_week.date = CURDATE()
)
AND term = (
	SELECT
		id
	FROM
		school_term
	WHERE
		school_term.term_start <= CURDATE()
	AND CURDATE() <= school_term.term_end
);


DECLARE CONTINUE HANDLER FOR NOT found
SET outer_done = 1;

OPEN app_cursor;

loop_app :
LOOP
	FETCH app_cursor INTO appointment_id_vis,
	course_no_vis,
	virtual_image_id_vis,
	term_vis,
	start_week_vis,
	end_week_vis,
	weekday_vis,
	start_class_vis,
	end_class_vis,
	update_date_vis;


IF outer_done <> 1 THEN
	SELECT
		start_date INTO start_time_vis
	FROM
		system_time
	WHERE
		system_time.section = start_class_vis;

SELECT
	end_date INTO end_time_vis
FROM
	system_time
WHERE
	system_time.section = end_class_vis;

SELECT
	COUNT(username) INTO student_count_vis
FROM
	report_schedule
WHERE
	course_no = course_no_vis
AND class_date = CURDATE()
AND start_class = start_class_vis
AND end_class = end_class_vis;

INSERT INTO virtual_image_schedule (
	appointment_id,
	virtual_image_id,
	term,
	start_week,
	end_week,
	weekday,
	start_class,
	end_class,
	update_date,
	start_time,
	end_time,
	student_count
)
VALUES
	(
		appointment_id_vis,
		virtual_image_id_vis,
		term_vis,
		start_week_vis,
		end_week_vis,
		weekday_vis,
		start_class_vis,
		end_class_vis,
		update_date_vis,
		start_time_vis,
		end_time_vis,
		student_count_vis
	);


END
IF;


IF outer_done = 1 THEN
	LEAVE loop_app;


END
IF;


END
LOOP
;

CLOSE app_cursor;


END ;;
DELIMITER ;
