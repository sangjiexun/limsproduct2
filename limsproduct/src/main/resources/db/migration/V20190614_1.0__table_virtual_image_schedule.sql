-- 功能：新建课表虚拟镜像推送表
-- 作者：杨新蔚
-- 日期：2019-06-14
DROP TABLE IF EXISTS `virtual_image_schedule`;
CREATE TABLE `virtual_image_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `appointment_id` int(11) DEFAULT NULL COMMENT '排课id',
  `virtual_image_id` varchar(255) DEFAULT NULL COMMENT '虚拟镜像id',
  `term` int(11) DEFAULT NULL COMMENT '学期',
  `start_week` int(11) DEFAULT NULL COMMENT '开始周次',
  `end_week` int(11) DEFAULT NULL COMMENT '结束周次',
  `weekday` int(11) DEFAULT NULL COMMENT '星期几',
  `start_class` int(11) DEFAULT NULL COMMENT '开始节次',
  `end_class` int(11) DEFAULT NULL COMMENT '结束节次',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `student_count` int(11) DEFAULT NULL COMMENT '学生数量',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5693 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT comment='课表虚拟镜像推送表';
