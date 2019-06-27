-- 批次表中增加最大可选组数/人
-- 陈乐为
ALTER TABLE `timetable_batch`
MODIFY COLUMN `start_date`  datetime NOT NULL COMMENT '选课开始时间' AFTER `flag`,
MODIFY COLUMN `end_date`  datetime NOT NULL COMMENT '选课结束时间' AFTER `start_date`,
ADD COLUMN `max_group_num`  int(11) NULL DEFAULT 1 COMMENT '最大可选组数/人' AFTER `self_id`;