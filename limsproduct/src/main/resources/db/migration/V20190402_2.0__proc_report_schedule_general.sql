-- 功能：获取所有学期的老师课表数据
-- 作者：陈乐为(徐明杭添加)
-- 日期：2019-04-02
drop PROCEDURE if exists `proc_report_schedule_general_0`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_report_schedule_general_0`()
BEGIN
		
	declare termflag int default 0;

	declare term_id int;

	declare termcursor cursor for
	select st.id from school_term st;
	
	declare continue handler for not found set termflag =1;

	open termcursor;
		loop_termid:loop
			fetch termcursor into term_id;
				if termflag = 1 then
					leave loop_termid;
				end if;
				call proc_report_schedule_general_1(term_id);
		end loop;
	close termcursor;	

END;;
DELIMITER ;