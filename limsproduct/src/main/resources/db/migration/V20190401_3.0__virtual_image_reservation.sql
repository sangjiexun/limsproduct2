-- 修改virtual_image_reservation表字段
-- 陈敬 2019年4月1日
ALTER TABLE virtual_image_reservation CHANGE is_used_by_student username VARCHAR(255) DEFAULT NULL;