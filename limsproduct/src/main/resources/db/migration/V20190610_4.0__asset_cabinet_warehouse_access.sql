-- 功能：修改字段长度
-- 作者：吴奇臻
-- 日期：2019-06-10

ALTER TABLE `asset_cabinet_warehouse_access`
MODIFY COLUMN `type`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库or申领' AFTER `flag`;

