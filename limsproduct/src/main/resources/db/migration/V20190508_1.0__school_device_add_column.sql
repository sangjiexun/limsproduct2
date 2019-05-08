-- school_device新增字段.备注和品牌
-- 刘博越 2019年5月8日
ALTER TABLE `school_device`
ADD COLUMN `device_brand`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '设备品牌',
ADD COLUMN `memo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注';

