-- ---------------------------------------------------------------------------------------------
-- 操作内容：更改数据库表用于导出入库单和领料单
-- 作者：吴奇臻
-- 操作时间：2019-04-30
-- ---------------------------------------------------------------------------------------------

ALTER TABLE `asset_storage_record`
ADD COLUMN `invoice_number`  varchar(255) NULL COMMENT '发票号' AFTER `total_price`,
ADD COLUMN `info`  varchar(255) NULL COMMENT '备注' AFTER `invoice_number`;

ALTER TABLE `asset_storage`
ADD COLUMN `audit_date`  datetime NULL COMMENT '审核通过时间' AFTER `cur_audit_level`;

ALTER TABLE `asset_receive`
ADD COLUMN `audit_date`  datetime NULL COMMENT '审核通过时间' AFTER `cur_audit_level`;

ALTER TABLE `asset_receive_record`
ADD COLUMN `info`  varchar(255) NULL COMMENT '备注' AFTER `cabinet_id`;