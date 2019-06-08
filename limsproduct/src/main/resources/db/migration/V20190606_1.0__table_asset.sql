-- 功能：增加物品柜门字段,删除无用表
-- 作者：吴奇臻
-- 日期：2019-06-06

drop table asset_adjust_record ;
drop table asset_app_audit ;
drop table asset_app_date ;
drop table asset_receive_audit ;
drop table asset_cabinet_open_time ;
ALTER TABLE `asset_receive_record`
ADD COLUMN `warehouse_id`  int(11) NULL COMMENT '智能柜柜门编号' AFTER `return_quantity`;

ALTER TABLE `asset_storage_record`
ADD COLUMN `warehouse_id`  int(11) NULL COMMENT '柜门编号' AFTER `info`;