-- 修改实验室预约表中使用人数类型
-- 陈乐为
ALTER TABLE `lab_reservation`
MODIFY COLUMN `number`  varchar(11) NULL DEFAULT NULL COMMENT '人数' AFTER `activity_category`;
