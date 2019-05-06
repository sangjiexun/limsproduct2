-- 增加report_schedule表字段
-- 廖文辉 2019-3-18
ALTER TABLE `report_schedule`
ADD COLUMN `center_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心名称' AFTER `ta_id`,
ADD COLUMN `center_manager_username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心负责人用户名' AFTER `center_name`,
ADD COLUMN `center_manager_cname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心负责人姓名' AFTER `center_manager_username`,
ADD COLUMN `lab_address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室地址' AFTER `center_manager_cname`,
ADD COLUMN `lab_room_classification`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室类别' AFTER `lab_address`,
ADD COLUMN `lab_room_type`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室类型' AFTER `lab_room_classification`,
ADD COLUMN `plan_student_number`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上课人数' AFTER `lab_room_type`,
ADD COLUMN `plan_hour`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上课时数' AFTER `plan_student_number`,
ADD COLUMN `plan_hour_students`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上课人时数' AFTER `plan_hour`;