-- 新增物资图片相关记录表
-- 吴奇臻
-- 2019/04/08
CREATE TABLE `asset_related_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物资相关图片记录表ID',
  `app_id` int(11) DEFAULT NULL COMMENT '申请id',
  `type` varchar(255) DEFAULT NULL COMMENT '申请类型，app,store,receive',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片url地址',
  `image_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `image_size` varchar(255) DEFAULT NULL COMMENT '图片大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


