-- 功能：排课学生判冲方法--判断学生的空闲情况
-- 作者：贺照易
-- 日期：2019-06-04
DROP PROCEDURE if exists `proc_judge_timetable_conflict_student`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_judge_timetable_conflict_student`(
IN in_students varchar(5000),
IN in_term int,
IN in_week int,
IN in_weekday int,
IN in_class int,
OUT out_conflict_rate int
)
Begin

	DECLARE i,count,ifConflict INT; 

	SET i=1;
	SET count=0;

	START TRANSACTION;

	SET @userlength=1+(LENGTH(in_students) - LENGTH(REPLACE(in_students,',','')));
	WHILE i<@userlength DO
		SET @username = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_students,',',i)),',',1));

		SELECT
			count( * ) into ifConflict
		FROM
		school_course_student scs
		LEFT JOIN school_course_detail scd ON scs.course_detail_no = scd.course_detail_no
		LEFT JOIN timetable_appointment ta ON ta.detail_id = scd.course_detail_no
		LEFT JOIN timetable_appointment_same_number tasn ON tasn.appointment_id = ta.id
		WHERE scs.student_number = @username
		AND tasn.start_week <= in_week
		AND tasn.end_week >= in_week
		AND tasn.weekday = in_weekday
		AND tasn.start_class <= in_class
		AND tasn.end_class >= in_class;

		if ifConflict <> 0 then
			SET count = count+1;
		END if;
		
		SELECT
			count( * ) into ifConflict 
		FROM
		timetable_group_students tgs
		LEFT JOIN timetable_appointment_group tag ON tag.group_id = tgs.group_id
		LEFT JOIN timetable_appointment ta ON ta.id = tag.appointment_id
		LEFT JOIN timetable_appointment_same_number tasn ON tasn.appointment_id = ta.id
		WHERE tgs.username = @username
		AND tasn.start_week <= in_week
		AND tasn.end_week >= in_week
		AND tasn.weekday = in_weekday
		AND tasn.start_class <= in_class
		AND tasn.end_class >= in_class;

		if ifConflict <> 0 then
			SET count = count+1;
		END if;

		SELECT
			count( * ) into ifConflict 
		FROM
		timetable_course_student tcs
		LEFT JOIN timetable_appointment ta ON ta.self_course_code = tcs.course_code_id
		LEFT JOIN timetable_appointment_same_number tasn ON tasn.appointment_id = ta.id
		WHERE tcs.student_number = @username
		AND tasn.start_week <= in_week
		AND tasn.end_week >= in_week
		AND tasn.weekday = in_weekday
		AND tasn.start_class <= in_class
		AND tasn.end_class >= in_class;

		if ifConflict <> 0 then
			SET count = count+1;
		END if;
		
				if i = @userlength-1 then
			SET out_conflict_rate = (@userlength-1) - count;
		END if;

		SET i = i+1;

	END WHILE;
	COMMIT;

End

