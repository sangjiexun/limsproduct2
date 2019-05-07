-- 新增表{实验室物联硬件子表}
-- 陈乐为  2019年5月6日
DROP TABLE IF EXISTS `lab_room_agent_sub`;
CREATE TABLE `lab_room_agent_sub` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
`lab_id`  int(11) NULL DEFAULT NULL COMMENT '实验室id' ,
`agent_id`  int(11) NULL DEFAULT NULL COMMENT '硬件表id' ,
`type`  int(11) NULL DEFAULT 1 COMMENT '类型（1：父级，2：子级）' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

-- 添加学院数据{全校}
INSERT INTO `school_academy` (`academy_number`, `academy_name`, `is_vaild`, `academy_type`, `created_at`, `updated_at`) VALUES ('20190506', '全校', '1', NULL, '2019-05-06 18:08:02', '2019-05-06 18:07:59');
