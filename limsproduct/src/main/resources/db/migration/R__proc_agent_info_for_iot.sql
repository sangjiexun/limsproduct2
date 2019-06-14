-- 功能：获取门禁/电控的下发信息
-- 作者：陈乐为 2019年6月11日
DROP PROCEDURE if exists `proc_agent_info_for_iot`;
DELIMITER ;;

CREATE DEFINER = `root`@`%` PROCEDURE `proc_agent_info_for_iot`(IN `app_id` int,IN `app_type` varchar(40),IN `hardware_ip` varchar(40),IN `hardware_type` int)
BEGIN
	#Routine body goes here...
	#############################################实验室预约开始##############################################
	IF hardware_type=548 THEN
	# 预约人--门禁
	SELECT
	CONCAT('door_lab_',lres.lab_room,'_',lrtt.id,'_',lrg.id) AS id,
	lres.contacts AS username,
	ur.cname,
	vu.aclcard AS cardno,
	0 AS isAdmin,
	lrg.doorindex AS deviceindex,
	CONCAT(lres.lending_time,' ',lrtt.start_time) AS starttime,
	CONCAT(lres.lending_time,' ',lrtt.end_time) AS endtime
	FROM lab_reservation lres
	JOIN `user` ur ON ur.username=lres.contacts
	JOIN view_user vu ON vu.username=ur.username
	JOIN lab_reservation_time_table lrtt ON lrtt.lab_reservation_id=lres.id
	JOIN lab_room lr ON lr.id=lres.lab_room
	JOIN lab_room_agent lrg ON lrg.lab_room_id=lr.id AND lrg.hardware_type=hardware_type AND lrg.hardware_ip=hardware_ip
	WHERE lres.id=app_id

	UNION
	# 实验室管理员/物联管理员--门禁
	SELECT
	CONCAT('door_admin_',lrg.id,'_',lra.id) AS id,
	ur.username AS username,
	ur.cname,
	vu.aclcard AS cardno,
	1 AS isAdmin,
	lrg.doorindex AS deviceindex,
	'' AS starttime,
	'' AS endtime
	FROM lab_reservation lres
	JOIN lab_room lr ON lr.id=lres.lab_room
	JOIN lab_room_agent lrg ON lrg.lab_room_id=lr.id AND lrg.hardware_type=hardware_type AND lrg.hardware_ip=hardware_ip
	JOIN lab_room_admin lra ON lra.lab_room_id=lr.id AND (lra.type_id=1 OR lra.type_id=2)
	JOIN `user` ur ON ur.username=lra.username AND ur.enabled=1
	JOIN view_user vu ON vu.username=ur.username
	WHERE lres.id=app_id;
END IF;
IF hardware_type=550 THEN

	# 预约人--电源控制器
	SELECT
	CONCAT('inst_lab_',lres.lab_room,'_',lrtt.id,'_',lrg.id) AS id,
	lres.contacts AS username,
	ur.cname,
	vu.card_no AS cardno,
	0 AS isAdmin,
	lrg.doorindex AS deviceindex,
	CONCAT(lres.lending_time,' ',lrtt.start_time) AS starttime,
	CONCAT(lres.lending_time,' ',lrtt.end_time) AS endtime
	FROM lab_reservation lres
	JOIN `user` ur ON ur.username=lres.contacts
	JOIN view_user vu ON vu.username=ur.username
	JOIN lab_reservation_time_table lrtt ON lrtt.lab_reservation_id=lres.id
	JOIN lab_room lr ON lr.id=lres.lab_room
	JOIN lab_room_agent lrg ON lrg.lab_room_id=lr.id AND lrg.hardware_type=hardware_type AND lrg.hardware_ip=hardware_ip
	WHERE lres.id=app_id

	UNION
	# 实验室管理员--实验室电源控制器
	SELECT
	CONCAT('inst_dooradmin_',lrg.id,'_',lra.id) AS id,
	ur.username AS username,
	ur.cname,
	vu.card_no AS cardno,
	1 AS isAdmin,
	lrg.doorindex AS deviceindex,
	'' AS starttime,
	'' AS endtime
	FROM lab_reservation lres
	JOIN lab_room lr ON lr.id=lres.lab_room
	JOIN lab_room_agent lrg ON lrg.lab_room_id=lr.id AND lrg.hardware_type=hardware_type AND lrg.hardware_ip=hardware_ip
	JOIN lab_room_admin lra ON lra.lab_room_id=lr.id AND lra.type_id=1
	JOIN `user` ur ON ur.username=lra.username AND ur.enabled=1
	JOIN view_user vu ON vu.username=ur.username
	WHERE lres.id=app_id

	UNION
	# 实验室管理员--实验室设备电源控制器
	SELECT
	CONCAT('inst_admin_',lra.id,'_',lrg.id) AS id,
	ur.username AS username,
	ur.cname,
	vu.card_no AS cardno,
	1 AS isAdmin,
	lrg.doorindex AS deviceindex,
	'' AS starttime,
	'' AS endtime
	FROM lab_reservation lres
	JOIN lab_room lr ON lr.id=lres.lab_room
	JOIN lab_room_agent lrg ON lrg.lab_room_id=lr.id AND lrg.hardware_type=hardware_type AND lrg.hardware_ip=hardware_ip
	JOIN lab_room_device lrd ON lrd.lab_room_id=lr.id AND lrd.agent_id=lrg.id
	JOIN lab_room_admin lra ON lra.lab_room_id=lr.id AND lra.type_id=1
	JOIN `user` ur ON ur.username=lra.username AND ur.enabled=1
	JOIN view_user vu ON vu.username=ur.username
	WHERE lres.id=app_id;
END IF;
#############################################实验室预约结束##############################################

END;;
DELIMITER ;

