-- 功能：lab_open_up_academy表新增字段开放对象
-- 作者：贺照易
-- 日期：2019-06-18
ALTER TABLE `lab_open_up_academy` 
MODIFY COLUMN `academy_number`  VARCHAR(40) NULL DEFAULT NULL COMMENT '学院编号' AFTER `lab_room_id`,
ADD COLUMN `authority_name` varchar(255) COMMENT '开放对象' AFTER `academy_number`;
