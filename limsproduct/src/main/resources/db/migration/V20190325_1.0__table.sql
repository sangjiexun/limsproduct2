-- 修改asset表用于物资管理
-- 吴奇臻 2019-3-25
ALTER TABLE `asset`
ADD COLUMN `price`  double NULL COMMENT '参考单价' AFTER `qRCode_url`,
ADD COLUMN `factory`  varchar(255) NULL COMMENT '生产厂家' AFTER `price`,
ADD COLUMN `function`  varchar(255) NULL COMMENT '功能描述' AFTER `factory`;

-- 新建asset_classification表用于物资分类
-- 吴奇臻 2019-3-25
CREATE TABLE `asset_classification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(255) DEFAULT NULL COMMENT '物资类别中文名',
  `ename` varchar(255) DEFAULT NULL COMMENT '物资类别英文名',
  `info` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 插入asset_classification数据
-- 吴奇臻 2019-3-25
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('1', '化学试剂', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('2', '危化品药品', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('3', '实验耗材', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('4', '办公用品', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('5', '卫生洁具', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('6', '五金电器', NULL, NULL);
INSERT INTO `asset_classification` (`id`, `cname`, `ename`, `info`) VALUES ('7', '工具/量具', NULL, NULL);
