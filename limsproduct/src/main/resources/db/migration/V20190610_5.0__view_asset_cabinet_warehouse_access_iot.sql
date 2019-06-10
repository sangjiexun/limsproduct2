-- 智能柜数据视图-暂用
-- 吴奇臻
-- 2019-06-10
drop view if exists `view_asset_cabinet_warehouse_access_iot`;
CREATE VIEW `view_asset_cabinet_warehouse_access_iot` AS SELECT
	`acw`.`warehouse_code` AS `warehouse_code`,
	`ac`.`hardware_ip` AS `hardware_ip`,
	`ac`.`hardware_type` AS `hardware_type`,
	`cs`.`server_ip` AS `server_ip`,
	`vu`.`username` AS `username`,
	`vu`.`cname` AS `cname`,
	`vu`.`card_no` AS `card_no`,
	`acwa`.`create_date` AS `begin_time`,
	(
		`acwa`.`create_date` + INTERVAL 30 MINUTE
	) AS `end_time`
FROM
	(
		(
			(
				(
					`asset_cabinet_warehouse_access` `acwa`
					LEFT JOIN `asset_cabinet_warehouse` `acw` ON (
						(
							`acw`.`id` = `acwa`.`warehouse_id`
						)
					)
				)
				LEFT JOIN `asset_cabinet` `ac` ON (
					(
						`ac`.`id` = `acw`.`asset_cabinet_id`
					)
				)
			)
			LEFT JOIN `view_user` `vu` ON (
				(
					`vu`.`username` = `acwa`.`manager`
				)
			)
		)
		LEFT JOIN `common_server` `cs` ON (
			(`ac`.`server_id` = `cs`.`id`)
		)
	)