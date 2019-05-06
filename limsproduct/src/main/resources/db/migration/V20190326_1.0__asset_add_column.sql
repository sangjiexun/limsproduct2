-- 修改asset表新增中心字段
-- 刘博越 2019-3-26
ALTER TABLE `asset`
ADD COLUMN `center_id`  int(11) NULL COMMENT '中心id' AFTER `level`;