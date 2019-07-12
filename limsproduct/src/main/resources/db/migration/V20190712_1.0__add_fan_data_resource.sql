-- 创建数据源切换表
-- lay 2019年7月12日
DROP TABLE IF EXISTS `fan_data_source`;
CREATE TABLE `fan_data_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '数据库名称',
  `url` varchar(128) NOT NULL COMMENT '数据库地址',
  `username` varchar(32) NOT NULL COMMENT '数据库用户名',
  `password` varchar(32) NOT NULL COMMENT '数据库密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='数据源表'
ROW_FORMAT=DYNAMIC
;


