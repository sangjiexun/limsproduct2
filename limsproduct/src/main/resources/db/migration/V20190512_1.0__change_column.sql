-- 修改常规检查周次字段类型
-- 陈乐为
ALTER TABLE `routine_inspection`
MODIFY COLUMN `week`  int(11) NULL DEFAULT NULL COMMENT '周次' AFTER `term`;

