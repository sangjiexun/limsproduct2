-- 修改数据表字段类型
-- 陈乐为  2019年4月18日
ALTER TABLE `report_out_plan_lab_rate`
MODIFY COLUMN `per_course_hour`  float(11,2) NULL DEFAULT NULL COMMENT '课时数' AFTER `use_num`,
MODIFY COLUMN `per_stu_hour`  float(11,2) NULL DEFAULT NULL COMMENT '人时数' AFTER `per_course_hour`;

-- 增加数据表标志位字段
ALTER TABLE `report_out_plan_lab_rate`
ADD COLUMN `flag`  int(2) NULL COMMENT '标志位1：计划内，2：计划外' AFTER `use_app`;