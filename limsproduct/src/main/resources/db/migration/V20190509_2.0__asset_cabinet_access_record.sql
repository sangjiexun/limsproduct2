-- 新建出入库记录表
-- 吴奇臻 2019年5月9日
CREATE TABLE `asset_cabinet_access_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物资出入库记录表',
  `app_id` int(11) DEFAULT NULL COMMENT '入库id或申领id',
  `type` varchar(255) DEFAULT NULL COMMENT '类型，入库or申领',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `asset_id` int(11) DEFAULT NULL COMMENT '物资ID',
  `username` varchar(255) DEFAULT NULL COMMENT '发起人',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


