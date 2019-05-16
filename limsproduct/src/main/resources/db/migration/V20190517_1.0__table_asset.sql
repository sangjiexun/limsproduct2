-- 添加剩余库存字段
-- 吴奇臻 2019-5-16

ALTER TABLE `asset_storage`
ADD COLUMN `audit_user`  varchar(255) NULL COMMENT '签字人（最后一级审核人）' AFTER `audit_date`;

ALTER TABLE `asset_receive`
ADD COLUMN `audit_user`  varchar(255) NULL COMMENT '签字人（最后一级审核人）' AFTER `audit_date`;


