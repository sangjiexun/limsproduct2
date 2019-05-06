-- 修改设备维修审核状态值注释
-- 陈乐为
ALTER TABLE `device_repair`
MODIFY COLUMN `audit_stage`  int(2) NULL DEFAULT NULL COMMENT '审核阶段(0保存，1提交，2审核通过，3审核拒绝，4已验收，5未验收，6已填写，7已入账，8已验收未导出）' AFTER `content`;