-- 开放项目预约
-- 黄保钱 2019-4-26
-- 增加实验项目表字段
ALTER TABLE `operation_item`
ADD COLUMN `title` int(11) NULL DEFAULT NULL COMMENT '开发人职称',
ADD COLUMN `min_unit` int(11) NULL DEFAULT NULL COMMENT '实验最小单位',
ADD COLUMN `open_term` int(11) NULL DEFAULT NULL COMMENT '开放学期',
ADD COLUMN `open_weeks` int(11) NULL DEFAULT NULL COMMENT '开放周次',
ADD COLUMN `item_aim` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验目标,要求',
ADD COLUMN `item_theory` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验原理',
ADD COLUMN `item_method` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验方法',
ADD COLUMN `item_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验结果及分析' ,
ADD COLUMN `item_attention` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验注意事项',
ADD COLUMN `item_question` int(11) NULL COMMENT '思考题',
ADD COLUMN `item_result_type` int(11) NULL COMMENT '实验结果形式',
ADD COLUMN `item_budget` decimal(40, 2) NULL DEFAULT NULL COMMENT '实验经费预算',
ADD COLUMN `audit_time` datetime(0) NULL COMMENT '审核通过时间',
ADD COLUMN `update_time` datetime(0) NULL COMMENT '最后修改时间',
ADD COLUMN `update_user` varchar(255) NULL DEFAULT NULL COMMENT '最后修改人',
ADD COLUMN `open_grade` int(11) NULL DEFAULT NULL COMMENT '开放年级',
ADD FOREIGN KEY (`min_unit`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`title`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`open_term`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`open_weeks`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`item_question`) REFERENCES `common_document` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`item_result_type`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`open_grade`) REFERENCES `c_dictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
ADD FOREIGN KEY (`update_user`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- 增加实验开放教师（多对多）表
DROP TABLE IF EXISTS `item_open_teacher`;
CREATE TABLE `item_open_teacher`  (
  `id` int(11) NOT NULL COMMENT '编号' AUTO_INCREMENT,
  `operation_item` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operation_item`(`operation_item`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  FOREIGN KEY (`operation_item`) REFERENCES `operation_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实验开放教师（多对多）' ROW_FORMAT = Dynamic;

-- 增加相关实验室人员（多对多）表
DROP TABLE IF EXISTS `item_lab_users`;
CREATE TABLE `item_lab_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `operation_item` int(11) NULL COMMENT '项目id',
  `username` varchar(255) NULL COMMENT '实验室人员工号',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`operation_item`) REFERENCES `operation_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '相关实验室人员（多对多）' ROW_FORMAT = Dynamic;

-- 增加项目计划（多对多）表
DROP TABLE IF EXISTS `item_plans`;
CREATE TABLE `item_plans`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `item_id` int(11) NULL COMMENT '项目id',
  `self_course_id` int(11) NULL COMMENT 'timetable_self_course的id',
  `start_time` datetime(0) NULL COMMENT '选课开始时间',
  `end_time` datetime(0) NULL COMMENT '选课结束时间',
  `type` int(11) NULL COMMENT '排课类型（0-不分批选；1-分批自选；2-不分批直排；3-分批直排）',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `operation_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`self_course_id`) REFERENCES `timetable_self_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目计划（多对多）' ROW_FORMAT = Dynamic;

-- 增加相关物资（多对多）表
DROP TABLE IF EXISTS `item_assets`;
CREATE TABLE `item_assets`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '相关物资（多对多）表主键',
  `operation_item` int(11) NULL COMMENT 'operation_item表外键',
  `asset_id` int(11) NULL COMMENT 'asset表外键',
  `amount` int(11) NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`operation_item`) REFERENCES `operation_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '相关物资（多对多）' ROW_FORMAT = Dynamic;

-- 增加字典数据
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_nature', '基础性', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_nature', '探究性', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('3', 'c_operation_item_nature', '综合性', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('4', 'c_operation_item_nature', '其它', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_title', '教授', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_title', '副教授', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_min_unit', '组', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_min_unit', '个人', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_app', '演示实验', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_app', '分组实验', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('0', 'c_operation_item_open_grade', '所有年级', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_open_grade', '一年级', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_open_grade', '二年级', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('3', 'c_operation_item_open_grade', '三年级', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('4', 'c_operation_item_open_grade', '四年级', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('0', 'c_operation_item_open_term', '所有学期', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_open_term', '春学期', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_open_term', '秋学期', b'1', NULL);

INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('0', 'c_operation_item_result_type', '实验报告', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('1', 'c_operation_item_result_type', '调查报告', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('2', 'c_operation_item_result_type', '论文', b'1', NULL);
INSERT INTO `c_dictionary`(`c_number`, `c_category`, `c_name`, `enabled`, `code`) VALUES ('3', 'c_operation_item_result_type', '其他', b'1', NULL);

UPDATE `system_menu` SET `hyperlink` = '/openOperationItem/listOpenOperationItem?currpage=1&status=1&orderBy=9' WHERE `name` = '开放项目管理';