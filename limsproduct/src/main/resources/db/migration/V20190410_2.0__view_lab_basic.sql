-- 备份/修改基表六的视图
-- 陈乐为

drop view if exists `view_lab_basic`;
CREATE VIEW `view_lab_basic`AS
SELECT
	'12712' AS `schoolNumber`,
	`lab_room`.`lab_room_number` AS `labRoomNumber`,
	`lab_room`.`lab_room_name` AS `labRoomName`,
	`lab_room`.`lab_room_classification` AS `labRoomCategory`,
	`lab_room`.`lab_room_time_create` AS `labRoomTimeCreate`,
	`lab_room`.`lab_room_area` AS `labRoomArea`,
	`lab_room`.`lab_room_type` AS `labRoomType`,
	`lab_room`.`lab_room_subject` AS `subject`,
	'' AS `labPrizeNation`,
	'' AS `labPrizeProvince`,
	'' AS `labPrizePatent`,
	'' AS `labPrizeStudent`,
	'' AS `labPaperSciTeaching`,
	'' AS `labPaperSciResearch`,
	'' AS `labPaperKeyTeaching`,
	'' AS `labPaperKeyResearch`,
	'' AS `labPaperBook`,
	'' AS `labResearchNation`,
	'' AS `labResearchOther`,
	'' AS `labService`,
	'' AS `labTeachingNation`,
	'' AS `labTeachingOther`,
	'' AS `labGraduateMaster`,
	'' AS `labGraduateBachelor`,
	'' AS `labGraduateOther`,
	'' AS `labOpenItemCountInner`,
	'' AS `labOpenItemCountOutter`,
	'' AS `labOpenItemStudentInner`,
	'' AS `labOpenItemStudentOutter`,
	'' AS `labOpenItemHourInner`,
	'' AS `labOpenItemHourOutter`,
	'' AS `labPartTime`,
	'' AS `labCostTotal`,
	'' AS `labCostTeaching`,
	'' AS `yearName`,
	'' AS `yearId`
FROM
	`lab_room`
	;

