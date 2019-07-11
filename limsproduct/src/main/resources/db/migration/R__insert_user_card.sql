-- 历史数据处理{给初始卡号}
-- 陈乐为
INSERT INTO user_card (username, card_no, enabled) SELECT
	username,
	'88888888',
	'1'
FROM
	`user`
WHERE
	username NOT IN (
		SELECT
			username
		FROM
			user_card
		WHERE
			enabled = '1'
	)
AND enabled = 1
AND user_status = 1;