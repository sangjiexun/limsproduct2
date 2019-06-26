-- 功能：课程大纲修改字段
-- 作者：刘博越
-- 日期：2019-06-26
ALTER TABLE `operation_outline`
CHANGE COLUMN `course_ appraise` `course_appraise`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程考核方法' AFTER `teach_method`;




