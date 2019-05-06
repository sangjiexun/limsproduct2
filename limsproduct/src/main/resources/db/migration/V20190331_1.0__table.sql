-- 修改asset_app表用于物资管理
-- 吴奇臻 2019-3-31
ALTER TABLE `asset_app` 
ADD COLUMN `price` double(10, 2) NULL COMMENT '申请总金额' AFTER `second_audit_user`,
ADD COLUMN `category_id` int(11) NULL COMMENT '物资类别编号' AFTER `price`,
ADD COLUMN `begin_date` date NULL COMMENT '申购开始时间' AFTER `category_id`,
ADD COLUMN `end_date` date NULL COMMENT '申购结束时间' AFTER `begin_date`,
ADD COLUMN `academy_number` varchar(255) NULL COMMENT '申请学院编号' AFTER `end_date`,
ADD COLUMN `center_id` varchar(255) NULL COMMENT '中心id' AFTER `academy_number`;

-- 修改asset_cabinet表用于物品柜
-- 吴奇臻 2019-3-31
ALTER TABLE `asset_cabinet` 
ADD COLUMN `capacity` int(11) NULL COMMENT '物品柜剩余容量' AFTER `flag`;

-- 新建asset_storage表用于物资入库
-- 吴奇臻 2019-3-31
CREATE TABLE `asset_storage` (
  `id` int(11) NOT NULL,
  `app_id` int(11) DEFAULT NULL COMMENT '物资申购ID(申购入库）',
  `cabinet_id` int(11) DEFAULT NULL COMMENT '物品柜ID',
  `username` varchar(255) DEFAULT NULL COMMENT '入库人',
  `date` datetime DEFAULT NULL COMMENT '发起入库日期',
  `batch_number` varchar(255) DEFAULT NULL COMMENT '入库编号',
  `academy_number` varchar(255) DEFAULT NULL COMMENT '学院编号',
  `center_id` int(11) DEFAULT NULL COMMENT '实验中心id',
  `classification_id` int(11) DEFAULT NULL COMMENT '主要物品分类',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价',
  `invoice_number` varchar(255) DEFAULT NULL COMMENT '发票编号',
  `invoice_image` varchar(255) DEFAULT NULL COMMENT '发票图片',
  `godown_entry` varchar(255) DEFAULT NULL COMMENT '入库单',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


