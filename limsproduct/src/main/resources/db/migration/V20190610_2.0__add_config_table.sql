-- 创建实验室相关配置项表
-- 贺照易 2019年6月10日
DROP TABLE IF EXISTS `lab_relevant_config`;
CREATE TABLE `lab_relevant_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lab_room_id` int(11) NULL DEFAULT NULL COMMENT '实验室id',
  `config_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置项类别',
  `set_item` int(11) NULL DEFAULT NULL COMMENT '1是0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB 
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='实验室相关配置项表'
ROW_FORMAT=DYNAMIC
;


