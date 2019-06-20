-- 功能：修改字段类型
-- 作者：吴奇臻
-- 日期：2019-06-20
ALTER TABLE `asset_receive_record`
MODIFY COLUMN `quantity`  int(11) NULL DEFAULT NULL COMMENT '申领数量' AFTER `unit`;

delete from authority_menu where menu_id in (SELECT id from system_menu where identification='material.record');
DELETE from system_menu where identification='material.record';