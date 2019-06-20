-- 功能：实验室预约开放范围表旧数据迁移
-- 作者：贺照易
-- 日期：2019-06-20
INSERT INTO lab_open_up_academy(lab_room_id,academy_number,type)
	SELECT
	l.lab_room_id,
	l.academy_number,
	1 AS type
	FROM lab_open_academy l
