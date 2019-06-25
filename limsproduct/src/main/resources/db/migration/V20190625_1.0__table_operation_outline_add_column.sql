-- 功能：课程大纲新增字段
-- 作者：刘博越
-- 日期：2019-06-25
ALTER TABLE `operation_outline`
ADD COLUMN `course_hour`  decimal(10,2) NULL COMMENT '课程学时' AFTER `teach_way`,
ADD COLUMN `computer_course_hour`  decimal(10,2) NULL COMMENT '上机学时' AFTER `experiment_course_hour`,
ADD COLUMN `exercise_course_hour`  decimal(10,2) NULL COMMENT '习题学时' AFTER `computer_course_hour`,
ADD COLUMN `teach_method`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程教学方法' AFTER `input_credit`,
ADD COLUMN `course_ appraise`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程考核方法' AFTER `teach_method`,
ADD COLUMN `auditor`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '大纲审核人' AFTER `course_ appraise`;



