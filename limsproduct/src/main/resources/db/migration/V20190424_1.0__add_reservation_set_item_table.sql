-- 创建实验室预约配置项表
-- 贺照易 2019年4月24日
DROP TABLE IF EXISTS `reservation_set_item`;
CREATE TABLE `reservation_set_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lab_room_id` int(11) NULL DEFAULT NULL COMMENT '实验室id',
  `start_hour_inweek` double(11, 2) NULL DEFAULT NULL COMMENT '周内开放时间开始时间',
  `end_hour_inweek` double(11, 2) NULL DEFAULT NULL COMMENT '周内开放时间结束时间',
  `open_inweekend` int(11) NULL DEFAULT NULL COMMENT '是否周末开放',
  `start_hour_inweekend` double(11, 2) NULL DEFAULT NULL COMMENT '周末开放时间开始时间',
  `end_hour_inweekend` double(11, 2) NULL DEFAULT NULL COMMENT '周末开放时间结束时间',
  `item_type` int(11) NULL DEFAULT NULL COMMENT '标志位:0实验室配置项，1设备配置项',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reservation_setup_item_ibfk_1`(`lab_room_id`) USING BTREE
) ENGINE = InnoDB 
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='实验室预约配置项表'
ROW_FORMAT=DYNAMIC
;

-- 增加实验室预约禁用时间段表标志位字段
ALTER TABLE `lab_room_limit_time`
ADD COLUMN `type` int(11) NULL DEFAULT NULL COMMENT '标志位:0实验室配置项，1设备配置项' AFTER `flag`;

