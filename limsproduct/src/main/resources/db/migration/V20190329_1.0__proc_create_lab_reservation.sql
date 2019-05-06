-- 功能：实验室预约的保存方法
-- 作者：魏诚
-- 日期：2019-03-06
DROP PROCEDURE if exists `proc_create_lab_reservation`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_create_lab_reservation`(
IN in_username   varchar(255),
IN in_telephone   varchar(255),
IN in_labroom  int,
IN in_status  int,
IN in_timetableStyle  int,
IN in_termId   int,
IN in_number   int,
IN in_reason   varchar(255),
IN in_eventType   varchar(255),
IN in_eventName   varchar(255),
IN in_objectType   varchar(255),
IN in_content   varchar(255),
IN in_weekday varchar(255),
IN in_deviceOrLab int,
IN in_weeks varchar(255),
IN in_classes varchar(255),
IN in_reservationDate varchar(255),
IN in_startTime varchar(255),
IN in_endTime varchar(255),
IN in_createdDate   varchar(255),
IN in_updatedDate   varchar(255),
OUT out_labRId int
)
Begin

  	## 定义生成的timetable_appointment主键id
  	DECLARE var_myId INT;
  	DECLARE var_sameId INT;
  	DECLARE i,j,k,l INT;
  	DECLARE startClass,endClass INT;
  	DECLARE startWeek,endWeek INT;
	DECLARE out_timetable_id LONGTEXT;
	## 开始事务管理
	START TRANSACTION;
	##保存预约记录表
	insert into lab_reservation(
		lab_room	,
		contacts,
		event_name	,
		reservations	,
		remarks	,
		telephone,
		reason,
		number	,
		audit_results	,
		item_releasese	,
		reservation_type,
		user_type
	 )
	 VALUES(
		in_labroom  	,
		in_username,
		in_eventName   	,
		'reservations'   ,
		'remarks' ,
		in_telephone,
		in_reason,
		in_number,
		-1,
		-1,
		in_eventType,
		in_objectType
	);
  	SET var_myId = last_insert_id();
	SET out_labRId = last_insert_id();
	SET i=1;
	SET @weekdaylength=1+(LENGTH(in_weekday) - LENGTH(REPLACE(in_weekday,',','')));
	WHILE i<@weekdaylength DO
		SET @currWeekday = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(in_weekday,',',i)),',',1));
		## 调用课表保存存储过程
		call proc_create_timetable_appointment(in_username,'labroomApp',in_labroom,in_status,in_timetableStyle,
		                               in_termId, null,null,@currWeekday,in_weeks,in_classes,
																	 in_createdDate, in_updatedDate,out_timetable_id);
	  
		##保存预约记录子表
		insert into lab_reservation_time_table(
		  lab_reservation_id,
			timetable_id
		)
	  VALUES(
		  var_myId,
			out_timetable_id
		);
		SET i=i+1;
	END WHILE;
	
	COMMIT;  
	
End