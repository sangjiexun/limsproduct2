-- 功能：添加学院编号
-- 作者：吴奇臻
-- 日期：2019-06-24
ALTER TABLE `asset_receive`
ADD COLUMN `academy_number`  varchar(255) NULL COMMENT '学院编号' AFTER `reject_reason`;