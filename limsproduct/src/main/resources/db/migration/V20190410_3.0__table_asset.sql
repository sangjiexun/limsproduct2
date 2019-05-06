-- ---------------------------------------------------------------------------------------------
-- 操作内容：更改数据库表用于保存审核层级
-- 作者：吴奇臻
-- 操作时间：2019-04-10
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_app`
ADD COLUMN `cur_audit_level`  varchar(255) NULL COMMENT '当前审核层级权限名' AFTER `center_id`;

ALTER TABLE `asset_storage`
ADD COLUMN `cur_audit_level`  varchar(255) NULL COMMENT '当前审核层级权限名' AFTER `status`;

ALTER TABLE `asset_receive`
ADD COLUMN `cur_audit_level`  varchar(255) NULL COMMENT '当前审核层级权限名' AFTER `center_id`;
-- ---------------------------------------------------------------------------------------------
-- 操作内容：更改数据库表用于存储具体物资对应物品柜
-- 作者：吴奇臻
-- 操作时间：2019-04-10
-- ---------------------------------------------------------------------------------------------
ALTER TABLE `asset_app_record`
ADD COLUMN `cabinet_id`  int(255) NULL COMMENT '由申领到入库时所选择的物品柜id' AFTER `app_finalSpec`;

ALTER TABLE `asset_storage_record`
ADD COLUMN `cabinet_id`  int(11) NULL COMMENT '对应物品柜ID' AFTER `supplier`;

ALTER TABLE `asset_receive_record`
ADD COLUMN `cabinet_id`  int(11) NULL COMMENT '物品柜编号' AFTER `if_public`;
