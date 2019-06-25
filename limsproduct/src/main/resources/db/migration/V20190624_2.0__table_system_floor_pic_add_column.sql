-- 功能：添加资源容器地址
-- 作者：刘博越
-- 日期：2019-06-24
ALTER TABLE `system_floor_pic`
ADD COLUMN `photo_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径' AFTER `document_url`;

