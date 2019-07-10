-- 用户卡号转换视图
-- 陈乐为
-- 修改：增加user_card的id
drop view if exists `view_user`;

CREATE
ALGORITHM=UNDEFINED
DEFINER=`root`@`%`
SQL SECURITY DEFINER
VIEW `view_user`AS
SELECT
	`user_card`.`username` AS `username`,
	`user_card`.`card_no` AS `card_no`,
	concat(
		lpad(
			conv(
				substr(
					conv(
						`user_card`.`card_no`,
						10,
						16
					),
					3,
					2
				),
				16,
				10
			),
			5,
			'0'
		),
		lpad(
			conv(
				concat(
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						5,
						2
					),
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						7,
						2
					)
				),
				16,
				10
			),
			5,
			'0'
		)
	) AS `cardnumber`,
	concat(
		lpad(
			conv(
				substr(
					conv(
						`user_card`.`card_no`,
						10,
						16
					),
					3,
					2
				),
				16,
				10
			),
			5,
			'0'
		),
		lpad(
			conv(
				concat(
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						5,
						2
					),
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						7,
						2
					)
				),
				16,
				10
			),
			5,
			'0'
		)
	) AS `aclcard`,
	`u`.`cname` AS `cname`,
    user_card.id
FROM
	(
		`user_card`
		JOIN `user` `u` ON (
			(
				`u`.`username` = `user_card`.`username`
			)
		)
	)
WHERE
	(
		(`user_card`.`enabled` = 1)
		AND (`user_card`.`card_no` > 0)
	)
UNION
	SELECT
		`user_card`.`username` AS `username`,
		`user_card`.`card_no` AS `card_no`,
		concat(
			lpad(
				conv(
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						11,
						2
					),
					16,
					10
				),
				5,
				'0'
			),
			lpad(
				conv(
					concat(
						substr(
							conv(
								`user_card`.`card_no`,
								10,
								16
							),
							13,
							2
						),
						substr(
							conv(
								`user_card`.`card_no`,
								10,
								16
							),
							15,
							2
						)
					),
					16,
					10
				),
				5,
				'0'
			)
		) AS `cardnumber`,
		concat(
			lpad(
				conv(
					substr(
						conv(
							`user_card`.`card_no`,
							10,
							16
						),
						11,
						2
					),
					16,
					10
				),
				5,
				'0'
			),
			lpad(
				conv(
					concat(
						substr(
							conv(
								`user_card`.`card_no`,
								10,
								16
							),
							13,
							2
						),
						substr(
							conv(
								`user_card`.`card_no`,
								10,
								16
							),
							15,
							2
						)
					),
					16,
					10
				),
				5,
				'0'
			)
		) AS `aclcard`,
		`u`.`cname` AS `cname`,
        user_card.id
	FROM
		(
			`user_card`
			JOIN `user` `u` ON (
				(
					`u`.`username` = `user_card`.`username`
				)
			)
		)
	WHERE
		(
			(`user_card`.`enabled` = 1)
			AND (`user_card`.`card_no` < 0)
		) ;

