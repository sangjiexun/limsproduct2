-- 功能：获取iot中common_hwdlog考勤记录
-- 作者：徐明杭
-- 日期：2019-3-28
drop procedure if exists `proc_common_hwdlog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_common_hwdlog`(
In `hardware_ip` varchar(40),
IN `username` varchar(255) character set utf8,
IN `cname` VARCHAR(40) character set utf8,
IN `start_time` VARCHAR(40),
IN `end_time` VARCHAR(40),
IN `currentPage` int,
IN `pagesize` int
)
begin

DECLARE sql_1 LONGTEXT character set utf8 ;

set sql_1 = "
select 
u.cname,
u.username,
sa.academy_name,
ich.hardware_ip,
sc.class_name,
'' as major_name,
ich.datetime
from
iot.common_hdwlog ich
JOIN view_user vuc ON vuc.aclcard = ich.cardno
join user u on vuc.username = u.username
join school_academy sa on sa.academy_number = u.academy_number
left join school_classes sc on u.classes_number = sc.class_number
where 1=1";

IF hardware_ip <> ''
THEN set sql_1 = concat(sql_1," and ich.hardware_ip ='",hardware_ip,"'");
end if;

IF username <> ''
THEN set sql_1 = concat(sql_1," and (u.username ='",username,"'");
set sql_1 = concat(sql_1," or u.cname like '%",username,"%')");
end if;

if cname <> ''
THEN set sql_1 = concat(sql_1," and ich.cardname like %",cname,"%");
END IF;

if start_time <> ''
THEN set sql_1 = concat(sql_1," and ich.datetime >'",start_time,"'");
END IF;

if end_time <> ''
THEN set  sql_1 = concat(sql_1," and ich.datetime < '",end_time,"'");
END IF;

if currentPage <> 0
then set sql_1 = concat(sql_1," limit ",(currentPage-1)*pagesize,',',pagesize);
end if;
set @sql1 = sql_1;
PREPARE stmt1 FROM @sql1;
     EXECUTE stmt1;


END;;
DELIMITER ;