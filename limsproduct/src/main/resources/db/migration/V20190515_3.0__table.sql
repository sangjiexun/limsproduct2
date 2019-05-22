-- 开放项目相关报表字段修正
-- 贺照易 2019-5-15
update system_menu set hyperlink = '/log/listExperimentalSchedule?currpage=1' WHERE `name` = '开放项目相关报表';