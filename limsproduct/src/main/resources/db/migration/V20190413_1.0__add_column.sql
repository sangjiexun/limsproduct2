-- 添加楼层名字段
-- 陈乐为

ALTER TABLE `system_floor_pic`
ADD COLUMN `floor_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '楼层名（自定义）' AFTER `floor_no`;

