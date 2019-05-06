-- 数据表-计划外实验室使用统计数据表
-- 陈乐为 2019年4月17日

CREATE TABLE `report_out_plan_lab_rate` (
`term_id`  int(11) NULL DEFAULT NULL COMMENT '学期id' ,
`lab_id`  int(11) NULL DEFAULT NULL COMMENT '实验室id' ,
`lab_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室名称' ,
`lab_address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地点' ,
`use_date`  date NULL DEFAULT NULL COMMENT '使用日期' ,
`term_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期' ,
`usage_matt`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用事项' ,
`use_num`  int(11) NULL DEFAULT NULL COMMENT '人数' ,
`per_course_hour`  int(11) NULL DEFAULT NULL COMMENT '课时数' ,
`per_stu_hour`  int(11) NULL DEFAULT NULL COMMENT '人时数' ,
`use_obj`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用对象' ,
`use_app`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用途' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='计划外实验室使用统计数据表'
ROW_FORMAT=DYNAMIC
;

