-- 申购单添加课程编号
-- 黄保钱 2019-5-13
ALTER TABLE `asset_app`
ADD COLUMN `course_no` varchar(255) NULL COMMENT '课程编号';