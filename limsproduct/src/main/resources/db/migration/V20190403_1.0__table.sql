-- ---------------------------------------------------------------------------------------------
-- 操作内容：新建数据库表用于存储物品柜记录
-- 作者：吴奇臻
-- 操作时间：2019-04-03
-- ---------------------------------------------------------------------------------------------
CREATE TABLE `asset_cabinet_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cabinet_id` int(11) DEFAULT NULL COMMENT '物品柜编号',
  `asset_id` int(11) DEFAULT NULL COMMENT '物资编号',
  `quantity` int(11) DEFAULT NULL COMMENT '物资数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------------------------
-- 操作内容：修改入库记录表
-- 作者：吴奇臻
-- 操作时间：2019-04-03
-- ---------------------------------------------------------------------------------------------
drop table `asset_storage_record`;
CREATE TABLE `asset_storage_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入库记录表',
  `store_id` int(11) DEFAULT NULL COMMENT '物资入库单id',
  `asset_id` int(11) DEFAULT NULL COMMENT '物资id',
  `lab_id` int(11) DEFAULT NULL COMMENT '实验室id',
  `quantity` int(11) DEFAULT NULL COMMENT '入库物资数量',
  `price` decimal(16,2) DEFAULT NULL COMMENT '物资单价',
  `supplier` varchar(255) DEFAULT NULL COMMENT '供应商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------------------------
-- 操作内容：添加字段
-- 作者：吴奇臻
-- 操作时间：2019-04-03
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_receive`
ADD COLUMN `category_id`  int(11) NULL COMMENT '物资分类id' AFTER `operation_item_id`;

-- ---------------------------------------------------------------------------------------------
-- 操作内容：添加字段
-- 作者：吴奇臻
-- 操作时间：2019-04-03
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_classification`
ADD COLUMN `apply_audit`  varchar(255) NULL COMMENT '申领审核流程' AFTER `info`,
ADD COLUMN `storage_audit`  varchar(255) NULL COMMENT '入库审核流程' AFTER `apply_audit`,
ADD COLUMN `receive_audit`  varchar(255) NULL AFTER `storage_audit`,
ADD COLUMN `is_need_return`  int(11) NULL COMMENT '是否需要归还 0否 1是' AFTER `receive_audit`;