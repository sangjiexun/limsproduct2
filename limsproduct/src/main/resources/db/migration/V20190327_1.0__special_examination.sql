-- 添加special_examination表字段
-- 徐明杭 2019-3-26
alter table special_examination
add column `participant` varchar(255) null COMMENT '参与检查人员';
