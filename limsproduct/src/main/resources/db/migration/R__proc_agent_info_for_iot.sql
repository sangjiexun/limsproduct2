-- 功能：获取当天门禁/电控设备的全量下发信息
-- 作者：陈乐为 2019-6-20
DROP PROCEDURE if exists `proc_agent_info_for_iot`;
DELIMITER ;;

CREATE DEFINER = `root`@`%` PROCEDURE `proc_agent_info_for_iot`(IN `lab_id` int,IN `hardware_ip` varchar(40),IN `hardware_type` int)
BEGIN
	#Routine body goes here...
	IF hardware_type=548 THEN
		##########################################门禁开始###########################################
		###################实验室管理员开始######################
		SELECT
		CONCAT('door_admin_',lrg.id,'_',lra.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.aclcard AS cardno,
		1 AS isAdmin,
		lrg.doorindex AS deviceindex,
		'' AS starttime,
		'' AS endtime
		FROM lab_room_admin lra
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lra.lab_room_id
		JOIN view_user vu ON vu.username=lra.username
		JOIN `user` ur ON ur.username=vu.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip AND (lra.type_id=1 OR lra.type_id=2)
		###################实验室管理员结束######################
		###################排课上课教师开始######################
		UNION
		SELECT
		CONCAT('door_ta_',tlr.appointment_id,'_',tlr.id,'_',ttr.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.aclcard AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( tat.app_date, ' ', tat.start_time ) AS begin_time,
		concat( tat.app_date, ' ', tat.end_time ) AS end_time
		FROM lab_room_agent lrg
		JOIN timetable_lab_related tlr ON tlr.lab_id=lrg.lab_room_id
		JOIN timetable_appointment_time tat ON tat.appointment_id=tlr.appointment_id AND TO_DAYS(NOW())=TO_DAYS(tat.app_date)
		JOIN timetable_appointment ta ON ta.id=tat.appointment_id
		JOIN timetable_teacher_related ttr ON ttr.appointment_id=tat.appointment_id
		JOIN `user` ur ON ur.username=ttr.teacher
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND ta.timetable_style<>7
		###################排课上课教师结束######################
		###################实验室预约人开始######################
		UNION
		SELECT
		CONCAT('door_lab_',lres.id,'_',lrtt.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.aclcard AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( lres.lending_time, ' ', lrtt.start_time ) AS begin_time,
		concat( lres.lending_time, ' ', lrtt.end_time ) AS end_time
		FROM lab_reservation lres
		JOIN lab_reservation_time_table lrtt ON lrtt.lab_reservation_id=lres.id
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lres.lab_room
		JOIN `user` ur ON ur.username=lres.contacts
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lres.audit_stage=6 AND TO_DAYS(NOW())=TO_DAYS(lres.lending_time)
		###################实验室预约人结束######################
		###################工位预约人开始########################
		UNION
		SELECT
		CONCAT('door_sta_',lrsr.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.aclcard AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( lrsr.reservation_time, ' ', lrsr.start_time ) AS begin_time,
		concat( lrsr.reservation_time, ' ', lrsr.end_time ) AS end_time
		FROM lab_room_station_reservation lrsr
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lrsr.lab
		JOIN `user` ur ON ur.username=lrsr.username
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lrsr.result=1 AND TO_DAYS(lrsr.reservation_time)=TO_DAYS(NOW())
		###################工位预约人结束########################
		###################设备预约人开始########################
		UNION
		SELECT
		CONCAT('door_dev_',lrdr.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.aclcard AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		lrdr.begintime AS begin_time,
		lrdr.endtime AS end_time
		FROM lab_room_device_reservation lrdr
		JOIN lab_room_device lrd ON lrd.id=lrdr.lab_room_device
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lrd.lab_room_id
		JOIN `user` ur ON ur.username=lrdr.reserve_user
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lrdr.`status`=615 AND TO_DAYS(SUBSTRING(lrdr.begintime,1,10))=TO_DAYS(NOW());
		###################设备预约人结束########################
		##########################################门禁结束###########################################
	END IF;
	IF hardware_type=550 THEN
		##########################################电控开始###########################################
		###################实验室管理员开始######################
		############实验室电控开始############
		SELECT
		CONCAT('inst_dooradmin_',lrg.id,'_',lra.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		1 AS isAdmin,
		lrg.doorindex AS deviceindex,
		'' AS starttime,
		'' AS endtime
		FROM lab_room_admin lra
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lra.lab_room_id
		JOIN view_user vu ON vu.username=lra.username
		JOIN `user` ur ON ur.username=vu.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip AND (lra.type_id=1 OR lra.type_id=2)
		############实验室电控结束############
		##########实验室设备电控开始##########
		UNION
		SELECT
		CONCAT('inst_labadmin_',lra.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		1 AS isAdmin,
		lrg.doorindex AS deviceindex,
		'' AS starttime,
		'' AS endtime
		FROM lab_room_admin lra
		JOIN lab_room_device lrd ON lrd.lab_room_id=lra.lab_room_id
		JOIN lab_room_agent lrg ON lrg.id=lrd.agent_id
		JOIN view_user vu ON vu.username=lra.username
		JOIN `user` ur ON ur.username=vu.username
		WHERE lrd.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip AND lra.type_id=1
		##########实验室设备电控结束##########
		###################实验室管理员结束######################
		###################排课上课教师开始######################
		UNION
		SELECT
		CONCAT('inst_ta_',tlr.appointment_id,'_',tlr.id,'_',ttr.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( tat.app_date, ' ', tat.start_time ) AS begin_time,
		concat( tat.app_date, ' ', tat.end_time ) AS end_time
		FROM lab_room_agent lrg
		JOIN timetable_lab_related tlr ON tlr.lab_id=lrg.lab_room_id
		JOIN timetable_appointment_time tat ON tat.appointment_id=tlr.appointment_id AND TO_DAYS(NOW())=TO_DAYS(tat.app_date)
		JOIN timetable_appointment ta ON ta.id=tat.appointment_id
		JOIN timetable_teacher_related ttr ON ttr.appointment_id=tat.appointment_id
		JOIN `user` ur ON ur.username=ttr.teacher
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND ta.timetable_style<>7
		###################排课上课教师结束######################
		###################实验室预约人开始######################
		UNION
		SELECT
		CONCAT('inst_lab_',lres.id,'_',lrtt.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( lres.lending_time, ' ', lrtt.start_time ) AS begin_time,
		concat( lres.lending_time, ' ', lrtt.end_time ) AS end_time
		FROM lab_reservation lres
		JOIN lab_reservation_time_table lrtt ON lrtt.lab_reservation_id=lres.id
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lres.lab_room
		JOIN `user` ur ON ur.username=lres.contacts
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lres.audit_stage=6 AND TO_DAYS(NOW())=TO_DAYS(lres.lending_time)
		###################实验室预约人结束######################
		###################工位预约人开始########################
		UNION
		SELECT
		CONCAT('inst_sta_',lrsr.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		concat( lrsr.reservation_time, ' ', lrsr.start_time ) AS begin_time,
		concat( lrsr.reservation_time, ' ', lrsr.end_time ) AS end_time
		FROM lab_room_station_reservation lrsr
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lrsr.lab
		JOIN `user` ur ON ur.username=lrsr.username
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lrsr.result=1 AND TO_DAYS(lrsr.reservation_time)=TO_DAYS(NOW())
		###################工位预约人结束########################
		###################设备预约人开始########################
		############设备电控开始############
		UNION
		SELECT
		CONCAT('inst_dev_',lrdr.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		lrdr.begintime AS begin_time,
		lrdr.endtime AS end_time
		FROM lab_room_device_reservation lrdr
		JOIN lab_room_device lrd ON lrd.id=lrdr.lab_room_device
		JOIN lab_room_agent lrg ON lrg.id=lrd.agent_id
		JOIN `user` ur ON ur.username=lrdr.reserve_user
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrd.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lrdr.`status`=615 AND TO_DAYS(SUBSTRING(lrdr.begintime,1,10))=TO_DAYS(NOW())
		############设备电控结束############
		############门禁电控开始############
		UNION
		SELECT
		CONCAT('inst_labdev_',lrdr.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		0 AS isAdmin,
		lrg.doorindex AS deviceindex,
		lrdr.begintime AS begin_time,
		lrdr.endtime AS end_time
		FROM lab_room_device_reservation lrdr
		JOIN lab_room_device lrd ON lrd.id=lrdr.lab_room_device
		JOIN lab_room_agent lrg ON lrg.lab_room_id=lrd.lab_room_id
		JOIN `user` ur ON ur.username=lrdr.reserve_user
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrg.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip
		AND lrdr.`status`=615 AND TO_DAYS(SUBSTRING(lrdr.begintime,1,10))=TO_DAYS(NOW())
		############门禁电控结束############
		###################设备预约人结束########################
		###################设备管理员开始########################
		UNION
		SELECT
		CONCAT('inst_admin_',lrd.id,'_',lrg.id,'_',vu.id) AS id,
		vu.username,
		ur.cname,
		vu.card_no AS cardno,
		1 AS isAdmin,
		lrg.doorindex AS deviceindex,
		'' AS begin_time,
		'' AS end_time
		FROM lab_room_device lrd
		JOIN lab_room_agent lrg ON lrg.id=lrd.agent_id
		JOIN `user` ur ON ur.username=lrd.manager_user
		JOIN view_user vu ON vu.username=ur.username
		WHERE lrd.lab_room_id=lab_id AND lrg.hardware_ip=hardware_ip;
		###################设备管理员结束########################
		##########################################电控结束###########################################
	END IF;
END;;
DELIMITER ;

