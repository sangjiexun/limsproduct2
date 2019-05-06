-- 修改asset_storage表用于物资管理
-- 吴奇臻 2019-3-31
ALTER TABLE `asset_storage`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;


-- 新建asset_storage_record表用于物资入库明细
-- 吴奇臻 2019-3-31
CREATE TABLE `asset_storage_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入库记录表',
  `store_id` int(11) DEFAULT NULL COMMENT '物资入库单id',
  `asset_id` int(11) DEFAULT NULL COMMENT '物资id',
  `lab_id` int(11) DEFAULT NULL COMMENT '实验室id',
  `app_usage` varchar(255) DEFAULT NULL COMMENT '申请用途',
  `app_quantity` int(11) DEFAULT NULL COMMENT '申购物资数量',
  `app_price` decimal(16,2) DEFAULT NULL COMMENT '物资单价',
  `app_supplier` varchar(255) DEFAULT NULL COMMENT '供应商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;




