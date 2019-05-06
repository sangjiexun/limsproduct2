-- 增长实验室安全管理专项检查的检查内容和整改内容的字段长度
-- 黄保钱 2019-3-15
ALTER TABLE `special_examination`
MODIFY COLUMN `check_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查内容',
MODIFY COLUMN `rectification` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '整改';