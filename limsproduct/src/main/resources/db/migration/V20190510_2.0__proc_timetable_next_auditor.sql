DROP PROCEDURE IF EXISTS `proc_timetable_next_auditor`;

delimiter ;;

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_timetable_next_auditor`(IN `auth_name` varchar(40),IN `business_type` varchar(255),IN `business_uid` varchar(255))
BEGIN

	IF auth_name = 'LABMANAGER' THEN
	  IF `business_type` = 'CloseTimetableAudit' THEN
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN school_course AS sc ON sc.course_no=ta.course_no AND sc.course_no=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1
				WHERE ta.`adjust_status` = 16
		  UNION
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN timetable_self_course AS tsc ON tsc.id=ta.self_course_code AND tsc.id=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1
				WHERE ta.`adjust_status` = 16;
		  END IF;
	  IF `business_type` = 'AdjustTimetableAudit' THEN
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN school_course AS sc ON sc.course_no=ta.course_no AND sc.course_no=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1
				WHERE ta.`status` != 15 and ta.`adjust_status` = 1
		  UNION
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN timetable_self_course AS tsc ON tsc.id=ta.self_course_code AND tsc.id=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1
				WHERE ta.`status` != 15 and ta.`adjust_status` = 1;
		  END IF;
	  IF `business_type` <> 'AdjustTimetableAudit' and `business_type` = 'CloseTimetableAudit' THEN
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN school_course AS sc ON sc.course_no=ta.course_no AND sc.course_no=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1
		  UNION
		  SELECT DISTINCT
			  lra.username
			  FROM timetable_appointment AS ta
			  JOIN timetable_self_course AS tsc ON tsc.id=ta.self_course_code AND tsc.id=business_uid
			  JOIN timetable_lab_related AS tlr ON tlr.appointment_id=ta.id
			  JOIN lab_room_admin AS lra ON tlr.lab_id=lra.lab_room_id AND lra.type_id=1;
		END IF;
	END IF;

	IF auth_name = 'PRESECTEACHING' OR auth_name = 'DEAN' THEN
		SELECT DISTINCT
				ua.user_id
			FROM user_authority AS ua
			JOIN authority AS aa ON aa.id=ua.authority_id AND aa.authority_name=auth_name;
	END IF;

	IF auth_name <> 'LABMANAGER' AND auth_name <> 'PRESECTEACHING' and auth_name <> 'DEAN' THEN
		CALL proc_academy_next_auditor(`auth_name`,`business_uid`);
	END IF;
END;;
delimiter ;