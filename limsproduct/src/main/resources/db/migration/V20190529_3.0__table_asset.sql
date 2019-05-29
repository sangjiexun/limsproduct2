-- 修改asset相关表
-- 吴奇臻 2019-5-18

ALTER TABLE `asset_cabinet`
ADD COLUMN `type`  int(11) NULL COMMENT '物品柜类型(1普通柜，2智能柜）' AFTER `capacity`,
ADD COLUMN `hardware_ip`  varchar(255) NULL AFTER `type`,
ADD COLUMN `hardware_type`  varchar(255) NULL AFTER `hardware_ip`,
ADD COLUMN `server_id`  varchar(255) NULL COMMENT '服务器id' AFTER `hardware_type`;

ALTER TABLE `asset_cabinet`
ADD COLUMN `location`  varchar(255) NULL COMMENT '存放地点' AFTER `server_id`;

ALTER TABLE `asset_cabinet_warehouse_access_record` DROP FOREIGN KEY `asset_cabinet_warehouse_access_record_ibfk_1`;

ALTER TABLE `asset_cabinet_warehouse`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;






