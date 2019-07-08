-- 功能：根据room_num获取实验室下的门禁记录,增加按门禁时间排序
-- 作者：陈乐为 2019-7-8 增加状态

DROP PROCEDURE if exists `proc_common_hwdlog_roomId`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_common_hdwlog_roomId`(In `room_num` varchar(40),
IN `username` varchar(255) character set utf8,
IN `cname` VARCHAR(40) character set utf8,
IN `start_time` VARCHAR(40),
IN `end_time` VARCHAR(40),
IN `currentPage` int,
IN `pagesize` int)
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
ich.datetime,
ich.status
from
iot.common_hdwlog ich
JOIN view_user vuc ON ((vuc.aclcard = ich.cardno OR vuc.cardnumber = ich.cardno OR vuc.card_no = ich.cardno) OR (ich.cardno=0 AND ich.cardname=vuc.username))
join user u on vuc.username = u.username
join school_academy sa on sa.academy_number = u.academy_number
left join school_classes sc on u.classes_number = sc.class_number
where 1=1";

IF room_num <> ''
THEN set sql_1 = concat(sql_1," and ich.hardware_ip in (select lra.hardware_ip from iot.lab_room_agent lra where lra.room_num ='",room_num,"')");
end if;

IF username <> ''
THEN set sql_1 = concat(sql_1," and (u.username ='",username,"'");
set sql_1 = concat(sql_1," or u.cname like '%",username,"%')");
end if;

if cname <> ''
THEN set sql_1 = concat(sql_1," and ich.cardname like '%",cname,"%'");
END IF;

if start_time <> ''
THEN set sql_1 = concat(sql_1," and ich.datetime >'",start_time,"'");
END IF;

if end_time <> ''
THEN set  sql_1 = concat(sql_1," and ich.datetime < '",end_time,"'");
END IF;

set sql_1 = concat(sql_1," ORDER BY ich.datetime desc");

if currentPage <> 0
then set sql_1 = concat(sql_1," limit ",(currentPage-1)*pagesize,',',pagesize);
end if;
set @sql1 = sql_1;
PREPARE stmt1 FROM @sql1;
     EXECUTE stmt1;


END