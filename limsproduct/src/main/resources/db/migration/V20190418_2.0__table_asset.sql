-- ---------------------------------------------------------------------------------------------
-- 操作内容：更改数据库表用于保存单项总价
-- 作者：吴奇臻
-- 操作时间：2019-04-18
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_storage_record`
ADD COLUMN `total_price`  double(10,2) NULL COMMENT '单项总价' AFTER `cabinet_id`;
-- ---------------------------------------------------------------------------------------------
-- 操作内容：修改字段名解决数据获取不到的问题
-- 作者：吴奇臻
-- 操作时间：2019-04-18
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_cabinet_record`
CHANGE COLUMN `quantity` `stock_number`  int(11) NULL DEFAULT NULL COMMENT '物资数量' AFTER `asset_id`;
