-- 增加课表日历菜单
-- 黄保钱 2019-4-26
-- 更新链接
INSERT INTO `system_menu`(`name`, `project_name`, `order_number`, `identification`, `is_used`, `hyperlink`, `parent_id`) VALUES ('课表日历', 'limsproduct', 221, NULL, 1, '/timetable/allTimetableAppointment', 1);
