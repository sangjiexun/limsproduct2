-- 建立审核流水号中间表
-- 黄保钱 2019-3-18
DROP TABLE IF EXISTS `audit_serial_number`;
CREATE TABLE `audit_serial_number`  (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号',
  `business_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核业务id',
  `business_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `enable` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`uuid`),
  UNIQUE INDEX(`uuid`) USING BTREE COMMENT 'uuid索引'
) COMMENT = '审核流水号中间表';