-- 添加剩余库存字段
-- 吴奇臻 2019-5-16

ALTER TABLE `asset_app_record`
ADD COLUMN `invoice_number`  varchar(255) NULL COMMENT '发票号' AFTER `total_price`,
ADD COLUMN `info`  varchar(255) NULL COMMENT '备注' AFTER `invoice_number`;



