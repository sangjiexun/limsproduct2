ALTER TABLE `asset_storage`
ADD COLUMN `reject_reason`  varchar(255) NULL COMMENT '拒绝原因' AFTER `apply_date`;

ALTER TABLE `asset_receive`
ADD COLUMN `reject_reason`  varchar(255) NULL COMMENT '拒绝原因' AFTER `audit_user`;

ALTER TABLE `asset_app`
ADD COLUMN `reject_reason`  varchar(255) NULL COMMENT '拒绝原因' AFTER `course_no`;
