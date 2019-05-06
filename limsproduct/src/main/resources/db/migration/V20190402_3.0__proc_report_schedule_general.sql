-- --------------------------------------------------------------------------------------
-- 功能：获取指定学期的老师课表数据
-- 作者：陈乐为(徐明杭添加)
-- 日期：2019-04-02
drop PROCEDURE if exists `proc_report_schedule_general_1`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_report_schedule_general_1`(IN termId int)

begin
	declare location int;
	declare startweek int;
	declare endweek int;
	declare weekday int;
	declare flag int default 0;
	declare t_id int;
	declare dataString varchar(255) default "";
	declare appointmentId int;

	declare cur cursor for 
	select tasn.id from timetable_appointment_same_number tasn;
	declare continue handler for not found set flag = 1;

	open cur;
		loop_apid:loop
				fetch cur into t_id;
			if flag=1 then
				leave loop_apid;
			end if;
			set appointmentId = (select appointment_id from timetable_appointment_same_number where id = t_id);
				set weekday = (select ta.weekday from timetable_appointment as ta where ta.id = appointmentId);
				set startweek = (select start_week from timetable_appointment_same_number where id = t_id);
				set endweek = (select end_week from timetable_appointment_same_number where id = t_id);
				set location = startweek;
				if startweek = endweek then
					call proc_get_date_only(termId,location,weekday,@a);
					select @a into dataString;
					call proc_report_schedule_general_2(termId,location,dataString,t_id);
				else
					while location <=endweek do
						call proc_get_date_only(termId,location,weekday,@a);
						select @a into dataString;
						call proc_report_schedule_general_2(termId,location,dataString,t_id);
						set location = location+1;
					end while;
				end if;
		end loop;
	close cur;
end

