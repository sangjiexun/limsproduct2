-- 功能：新增教学大纲教学目标表
-- 作者：刘博越
-- 日期：2019-06-26
DROP TABLE IF EXISTS `operation_outline_course_objective`;
CREATE TABLE `operation_outline_course_objective` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '课程目标id' ,
`operation_outline_id`  int(11) NULL COMMENT '对应课程大纲id' ,
`objective_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程目标名称' ,
`objective_content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程目标内容' ,
PRIMARY KEY (`id`)
)
;
-- 功能：新增教学大纲教学目标对应关系数据表
-- 作者：刘博越
-- 日期：2019-06-26
DROP TABLE IF EXISTS `operation_outline_course_objective_related`;
CREATE TABLE `operation_outline_course_objective_related` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id' ,
`operation_outline_id`  int(11) NULL COMMENT '所属大纲id' ,
`type`  int(11) NULL COMMENT '记录数据类型。0：毕业要求；1：课程内容；2：考核方法；3：课程目标达成度' ,
`objective_ids`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '对应课程目标id集合' ,
`objective_names`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '对应课程目标名称集合' ,
`graduation_requirement`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '毕业要求' ,
`requirement_point`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '毕业要求指标点' ,
`course_content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教学课程内容' ,
`course_requirement`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教学要求' ,
`course_hour`  int(11) NULL COMMENT '学时' ,
`method`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教学方式' ,
`appraise_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '考核环节' ,
`appraise_percentage`  int(11) NULL COMMENT '所占分值/百分比' ,
`appraise_detail`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '考核与评价细则' ,
`average_score`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '学生平均分（指代符号）' ,
`objective_completion_rate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '达成度计算结果' ,
PRIMARY KEY (`id`)
)
;


