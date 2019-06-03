-- 功能：创建业务流水单
-- 作者：陈乐为
DROP PROCEDURE if exists `proc_audit_serial_number`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_audit_serial_number`(IN `uuid` varchar(255),IN `business_app_uid` varchar(255),IN `business_type` varchar(255),IN `enable` bit(1))
BEGIN
	#Routine body goes here...
	UPDATE audit_serial_number SET enable=0 WHERE business_type=business_type AND business_id=business_app_uid;

	INSERT INTO audit_serial_number (uuid,business_id,business_type,`enable`) VALUES(uuid,business_app_uid,business_type,1);

	SELECT uuid FROM audit_serial_number WHERE business_type=business_type AND business_id=business_app_uid AND `enable`=1;

END;;
DELIMITER ;
