ALTER TABLE `asset_storage`
ADD COLUMN `apply_date`  date NULL COMMENT '采购日期' AFTER `audit_user`;