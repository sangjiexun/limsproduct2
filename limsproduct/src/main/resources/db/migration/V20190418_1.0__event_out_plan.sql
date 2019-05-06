-- 计划外实验室使用统计数据推送
-- 陈乐为 2019年4月18日

CREATE DEFINER=`root`@`localhost` 
EVENT `event_insert_report_out_plan`
ON SCHEDULE EVERY 1 DAY STARTS '2019-04-19 03:05:00'
ON COMPLETION NOT PRESERVE
ENABLE
COMMENT '计划外实验室使用统计数据推送'
DO
CALL proc_report_out_plan_lab_rate;

