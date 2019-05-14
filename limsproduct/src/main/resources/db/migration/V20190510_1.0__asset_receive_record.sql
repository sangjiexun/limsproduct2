-- 添加字段
-- 吴奇臻 2019年5月10日
ALTER TABLE `asset_receive_record`
ADD COLUMN `return_quantity`  int(255) NULL COMMENT '余料归还数量' AFTER `info`;


