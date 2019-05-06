-- 修改系主任-根据课程取学院
-- 陈乐为
-- 重建
drop PROCEDURE if exists `proc_academy_next_auditor`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_academy_next_auditor`(IN `auth_name` varchar(40),IN `business_uid` varchar(255))
BEGIN
	DECLARE academy VARCHAR(255);
	DECLARE academy_flag INT DEFAULT 0;

	DECLARE academy_cursor CURSOR FOR
		SELECT DISTINCT `user`.academy_number
			FROM timetable_appointment AS ta
			JOIN timetable_self_course AS tsc ON tsc.id=ta.self_course_code AND tsc.id=business_uid
			JOIN `user` ON `user`.username=ta.created_by
		UNION
		SELECT DISTINCT usc.academy_number
			FROM timetable_appointment AS ta
			INNER JOIN school_course AS sc ON sc.course_no=ta.course_no AND sc.course_no=business_uid;
			#INNER JOIN `user` AS us ON us.username=ta.created_by ;
	
	DECLARE CONTINUE HANDLER FOR NOT found SET academy_flag = 1;
	
	OPEN academy_cursor;
		loop_academy:LOOP
			FETCH academy_cursor INTO academy;
			IF academy_flag = 1 THEN 
				LEAVE loop_academy;
			END IF;
			SELECT DISTINCT
				ua.user_id
			FROM user_authority AS ua
			JOIN authority AS aa ON aa.id=ua.authority_id AND aa.authority_name=auth_name
			JOIN `user` ON `user`.username=ua.user_id AND `user`.academy_number=academy;
		END LOOP;
	CLOSE academy_cursor;
END;;
DELIMITER ;