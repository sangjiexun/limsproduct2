-- 添加物品柜字段
-- 吴奇臻 2019-5-16

ALTER TABLE `asset_cabinet_access_record`
ADD COLUMN `cabinet_id`  int(11) NULL COMMENT '物品柜id' AFTER `quantity`;
-- 删除外键
-- 吴奇臻 2019-5-16
ALTER TABLE `asset_cabinet_warehouse` DROP FOREIGN KEY `asset_cabinet_warehouse_ibfk_1`;

-- 添加主键自增
-- 吴奇臻 2019-5-16
ALTER TABLE `asset_cabinet`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

-- 添加数据记录
-- 吴奇臻 2019-5-16
INSERT INTO `limsproduct`.`system_menu` (`name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) 
VALUES ('物资记录', 'limsproduct', '221', NULL, '1', '/lims/api/material/listAssetsCabinetRecordAPI', '42');

INSERT INTO `limsproduct`.`system_menu` (`name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`)
VALUES ( '物品柜管理', 'limsproduct', '223', NULL, '1', '/lims/api/material/listAssetsCabinetAPI', '42');
