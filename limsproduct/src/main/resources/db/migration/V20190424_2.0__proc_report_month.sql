-- 月报报表
-- 陈乐为
DROP PROCEDURE IF EXISTS `proc_report_month`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_report_month`(IN `term_id` int, IN `params` varchar(40), IN `start_date` varchar(20), IN `end_date` varchar(20),IN `currpage` int,IN `pagesize` int)
BEGIN
DECLARE my_sql VARCHAR(2500);
	SET my_sql="
		SELECT
		rs.academy_name,
		rs.center_name,
		rs.center_manager_cname,
		rs.lab_name,
		rs.lab_address,
		rs.class_date,
		rs.course_name,
		rs.lp_name,
		COUNT(rs.id) AS user_num,
		(rs.end_class-rs.start_class+1) AS course_class,
		((COUNT(rs.id))*(rs.end_class-rs.start_class+1)) AS user_class_num,
		rs.lab_room_classification,
		rs.lab_room_type,
		rs.teacher,
		rs.classes,
		'',
		''
		FROM report_schedule rs ";

	SET my_sql=CONCAT(my_sql,' WHERE rs.term_id=',term_id);
	
	# 中心/实验室/课程名/课程编号/教师名/工号/项目名
	IF params<>'' AND params <> 'null' THEN
		SET my_sql = CONCAT(my_sql," AND (rs.lab_name LIKE '%",params,"%' OR rs.center_name LIKE '%",params,"%' OR rs.course_name LIKE '%",params,
				"%' OR rs.course_no LIKE '%",params,"%' OR rs.teacher LIKE '%",params,"%' OR rs.job_no LIKE '%",params,"%' OR rs.lp_name LIKE '%",params,"%')");
	END IF;

	if start_date <> '' AND start_date <> 'null' 
	THEN set my_sql = concat(my_sql," and rs.class_date >'",start_date,"'");
	END IF;

	if end_date <> '' AND end_date <> 'null'
	THEN set  my_sql = concat(my_sql," and rs.class_date < '",end_date,"'");
	END IF;

	SET my_sql = CONCAT(my_sql," GROUP BY rs.course_no,rs.lab_name,rs.class_date");

	if currpage <> 0
	then set my_sql = concat(my_sql," limit ",(currpage-1)*pagesize,',',pagesize);
	end if;

SET @ms=my_sql;
PREPARE sl FROM @ms;
EXECUTE sl;
DEALLOCATE PREPARE sl;
END;;

DELIMITER ;