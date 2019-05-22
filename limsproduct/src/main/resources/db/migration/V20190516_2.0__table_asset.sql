-- 添加剩余库存字段
-- 吴奇臻 2019-5-16

ALTER TABLE `asset_cabinet_access_record`
ADD COLUMN `remain_quantity`  int(11) NULL COMMENT '剩余库存数' AFTER `cabinet_id`;
