-- 修改基表字段
-- 陈乐为

ALTER TABLE `basic_data_one`
CHANGE COLUMN `column1` `school_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL FIRST ,
CHANGE COLUMN `column2` `device_number`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `school_code`,
CHANGE COLUMN `column3` `type_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_number`,
CHANGE COLUMN `column4` `device_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `type_code`,
CHANGE COLUMN `col5` `device_version`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_name`,
CHANGE COLUMN `col6` `device_spec`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_version`,
CHANGE COLUMN `col7` `device_origin`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_spec`,
CHANGE COLUMN `col8` `country_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_origin`,
CHANGE COLUMN `col9` `unit_price`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `country_code`,
CHANGE COLUMN `col10` `buy_date`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `unit_price`,
CHANGE COLUMN `col11` `status_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `buy_date`,
CHANGE COLUMN `col12` `use_direction`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `status_code`,
CHANGE COLUMN `col13` `unit_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `use_direction`,
CHANGE COLUMN `col14` `unit_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `unit_code`,
CHANGE COLUMN `col15` `retire_date`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `unit_name`,
CHANGE COLUMN `col16` `year_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `retire_date`;

ALTER TABLE `basic_data_three`
CHANGE COLUMN `column1` `school_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL FIRST ,
CHANGE COLUMN `column2` `device_number`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `school_code`,
CHANGE COLUMN `column3` `type_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_number`,
CHANGE COLUMN `column4` `device_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `type_code`,
CHANGE COLUMN `col5` `unit_price`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_name`,
CHANGE COLUMN `col6` `device_version`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `unit_price`,
CHANGE COLUMN `col7` `device_spec`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_version`,
CHANGE COLUMN `col8` `teach`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `device_spec`,
CHANGE COLUMN `col9` `scientific`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `teach`,
CHANGE COLUMN `col10` `social_service`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `scientific`,
CHANGE COLUMN `col11` `open_hours`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `social_service`,
CHANGE COLUMN `col12` `sample_count`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `open_hours`,
CHANGE COLUMN `col13` `training_student`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `sample_count`,
CHANGE COLUMN `col14` `training_teacher`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `training_student`,
CHANGE COLUMN `col15` `training_others`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `training_teacher`,
CHANGE COLUMN `col16` `teach_items`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `training_others`,
ADD COLUMN `search_items`  varchar(255) NULL AFTER `teach_items`,
ADD COLUMN `social_items`  varchar(255) NULL AFTER `search_items`,
ADD COLUMN `prize_country`  varchar(255) NULL AFTER `social_items`,
ADD COLUMN `prize_province`  varchar(255) NULL AFTER `prize_country`,
ADD COLUMN `patent_teacher`  varchar(255) NULL AFTER `prize_province`,
ADD COLUMN `patent_student`  varchar(255) NULL AFTER `patent_teacher`,
ADD COLUMN `paper_sci`  varchar(255) NULL AFTER `patent_student`,
ADD COLUMN `paper_caj`  varchar(255) NULL AFTER `paper_sci`,
ADD COLUMN `charge_person`  varchar(255) NULL AFTER `paper_caj`,
ADD COLUMN `year_code`  varchar(255) NULL AFTER `charge_person`;

