-- 功能：增加记录表，删除无用表
-- 作者：吴奇臻
-- 日期：2019-06-06

CREATE TABLE `asset_cabinet_warehouse_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `warehouse_id` int(11) DEFAULT NULL COMMENT 'warehouse表ID',
  `asset_id` int(11) DEFAULT NULL COMMENT 'asset表Id',
  `stock_number` int(11) DEFAULT NULL COMMENT '物资数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物资分配记录表';

ALTER TABLE `asset_cabinet_warehouse_access`
MODIFY COLUMN `cabinet_quantity`  int(11) NULL DEFAULT NULL COMMENT '物资进出数量' AFTER `status`,
ADD COLUMN `remain_quantity`  int(11) NULL COMMENT '物资剩余数量' AFTER `operation_item_id`;
ALTER TABLE `asset_cabinet_warehouse_access`
MODIFY COLUMN `type`  varchar(11) NULL DEFAULT NULL COMMENT '入库or申领' AFTER `flag`;
ALTER TABLE `asset_cabinet_warehouse_access` DROP FOREIGN KEY `asset_cabinet_warehouse_access_ibfk_2`;
ALTER TABLE `asset_cabinet_warehouse_access` DROP FOREIGN KEY `asset_cabinet_warehouse_access_ibfk_3`;
ALTER TABLE `asset_cabinet_warehouse_access` DROP FOREIGN KEY `asset_in_cabinet_ibfk_1`;