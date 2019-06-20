-- 功能：lab_open_up_academy表新增字段类型修改
-- 作者：贺照易
-- 日期：2019-06-20
ALTER TABLE `lab_open_up_academy` 
DROP COLUMN authority_name;
ALTER TABLE `lab_open_up_academy` 
ADD COLUMN `authority_id` int(11) COMMENT '开放对象权限id' AFTER `academy_number`;