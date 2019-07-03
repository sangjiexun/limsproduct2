-- 功能：删除实验室管理员、物联管理员{更新权限}
-- 作者：陈乐为
DROP PROCEDURE if exists `proc_lab_managers`;
DELIMITER ;;
CREATE DEFINER = `root`@`localhost` PROCEDURE `proc_lab_managers`(IN `manager` varchar(40),IN `auth_name` varchar(40),IN `typeId` int,IN `labId` int)
BEGIN
#管理权限
DECLARE flag int;
DECLARE cout int;
DECLARE cur cursor for
	SELECT COUNT(lra.id) FROM lab_room_admin lra WHERE lra.username=manager AND lra.type_id=typeId;
	declare continue handler for not found set flag = 1;

open cur;
	loop_apid:loop
		fetch cur into cout;
		if flag = 1 then
			leave loop_apid;
		end if;
		IF cout = 1 THEN
			#从实验室管理员表中删除
			DELETE FROM lab_room_admin WHERE username=manager AND type_id=typeId AND lab_room_id=labId;
			#删除管理员身份
			DELETE FROM user_authority WHERE user_id=manager AND authority_id IN (SELECT id FROM authority WHERE authority_name=auth_name);
		END IF;
		IF cout > 1 THEN
			#从实验室管理员表中删除
			DELETE FROM lab_room_admin WHERE username=manager AND type_id=typeId AND lab_room_id=labId;
		END IF;
	end loop;
close cur;

SELECT * FROM lab_room_admin WHERE username=manager AND type_id=typeId;

END;;
DELIMITER ;