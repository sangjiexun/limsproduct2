-- 实验室计划使用情况表
-- 陈乐为 2019年5月8日
ALTER TABLE `report_plan_lab_rate`
ADD COLUMN `base_id`  int(11) NULL COMMENT '基地id' AFTER `flag`,
ADD COLUMN `center_id`  int(11) NULL COMMENT '中心id' AFTER `base_id`;

