
drop PROCEDURE if EXISTS `proc_teacher_schedule`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_teacher_schedule`(IN `term_id` int,IN `term_code` int,IN `labname` varchar(255),IN `teacher` varchar(255),IN `weeks` int)
BEGIN
DECLARE my_sql VARCHAR(2500);
	SET my_sql="
	SELECT
		ta.id,
		vts.course_no,
		vts.course_name,
		vts.item_name,
		vts.weekday,
		vts.start_week,
		vts.end_week,
		st.section,
		`user`.username,
		`user`.cname,
		tutor.username AS tuser,
		tutor.cname AS tname,
		lr.id AS lab_id,
		lr.lab_room_name,
		ta.`status`,
		ta.timetable_style,
		ta.term,
		vts.start_class,
		vts.end_class
	FROM timetable_appointment ta
		JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
		JOIN view_teacher_schedule_1 vts ON vts.id=tasn.id
		LEFT JOIN timetable_teacher_related ttr ON ttr.appointment_id=ta.id
		LEFT JOIN `user` ON `user`.username=ttr.teacher
		LEFT JOIN timetable_tutor_related tur ON tur.appointment_id=ta.id
		LEFT JOIN `user` tutor ON tutor.username=tur.tutor
		LEFT JOIN timetable_lab_related tlr ON tlr.appointment_id=ta.id
		LEFT JOIN lab_room lr ON lr.id=tlr.lab_id
		JOIN system_time st ON st.section BETWEEN vts.start_class AND vts.end_class";

	SET my_sql=CONCAT(my_sql,' WHERE ta.term=',term_id);

	#IF weekday>0 THEN
	#	SET my_sql=CONCAT(my_sql,' AND vts.weekday=',weekday);
	#END IF;

	#IF term_code>0 THEN
	#	SET my_sql=CONCAT(my_sql," AND st.status=",term_code);
	#END IF;

	IF labname<>'' AND labname is not NULL THEN
		SET my_sql=CONCAT(my_sql," AND lr.lab_room_name LIKE ","'%",labname,"%'");
	END IF;

	IF teacher<>'' AND teacher is not NULL THEN
		SET my_sql=CONCAT(my_sql," AND (`user`.username=","'",teacher,"' OR tutor.username=","'",teacher,"')");
	END IF;

	IF weeks>0 THEN
		SET my_sql=CONCAT(my_sql," AND ",weeks," BETWEEN vts.start_week AND vts.vts.end_week");
	END IF;

	SET my_sql=CONCAT(my_sql,' ORDER BY vts.course_no,vts.weekday,st.section ');



SET @ms=my_sql;
PREPARE sl FROM @ms;
EXECUTE sl;
DEALLOCATE PREPARE sl;
END;;
DELIMITER ;