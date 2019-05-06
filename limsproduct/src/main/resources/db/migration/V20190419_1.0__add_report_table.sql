-- 删除计划外实验室使用数据表
-- 创建实验室使用数据表
-- 陈乐为 2019年4月19日
DROP TABLE IF EXISTS `report_out_plan_lab_rate`;
DROP TABLE IF EXISTS `report_plan_lab_rate`;
CREATE TABLE `report_plan_lab_rate` (
`term_id`  int(11) NULL DEFAULT NULL COMMENT '学期id' ,
`lab_id`  int(11) NULL DEFAULT NULL COMMENT '实验室id' ,
`lab_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室名称' ,
`lab_address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地点' ,
`use_date`  date NULL DEFAULT NULL COMMENT '使用日期' ,
`term_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期' ,
`usage_matt`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用事项' ,
`user_num`  int(11) NULL DEFAULT NULL COMMENT '人数' ,
`course_hour_num`  float(11,2) NULL DEFAULT NULL COMMENT '课时数' ,
`user_hour_num`  float(11,2) NULL DEFAULT NULL COMMENT '人时数' ,
`course_num`  float NULL DEFAULT NULL COMMENT '课次数' ,
`use_obj`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用对象' ,
`flag`  int(2) NULL DEFAULT NULL COMMENT '标志位1：计划内，2：计划外' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='实验室使用统计数据表'
ROW_FORMAT=DYNAMIC
;

-- 更新system_menu链接
-- 计划外实验室使用统计/计划内实验室使用统计/实验室课时课次统计表
update system_menu SET hyperlink='/log/listLabRoomUseUnplan?currpage=1&type=2' where identification='outPlan.statistics';
update system_menu SET hyperlink='/log/listLabRoomUseUnplan?currpage=1&type=1' where identification='inPlan.statistics';
update system_menu SET hyperlink='/log/listLabRoomUseUnplan?currpage=1&type=0' where identification='trainingStatistics.list';

