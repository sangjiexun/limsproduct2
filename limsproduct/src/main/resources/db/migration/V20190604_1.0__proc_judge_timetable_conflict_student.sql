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
		report_schedule re
		LEFT JOIN timetable_appointment ta ON ta.id = re.ta_id 
		WHERE
			ta.timetable_style <> 7 
		AND re.term_id = in_term
		AND re.weeks = in_week
		AND re.weekday = in_weekday
		AND re.start_class <= in_class 
		AND re.end_class >= in_class
		AND re.username = @username;

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

