-- 实验室开放范围配置表
-- 贺照易 2019年6月10日
DROP TABLE IF EXISTS `lab_open_up_academy`;
CREATE TABLE `lab_open_up_academy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lab_room_id` int(11) NULL DEFAULT NULL COMMENT '实验室id',
  `academy_number` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院编号',
  `type` int(11) NULL DEFAULT NULL COMMENT '标志位：1实验室预约2工位预约',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB 
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='实验室开放范围配置表'
ROW_FORMAT=DYNAMIC
;


