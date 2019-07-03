-- 物品柜表添加创建人和学院
-- 吴奇臻
ALTER TABLE `asset_cabinet`
ADD COLUMN `create_user`  varchar(255) NULL COMMENT '创建人' AFTER `cabinet_name`,
ADD COLUMN `academy_number`  varchar(255) NULL COMMENT '学院编号' AFTER `create_user`;