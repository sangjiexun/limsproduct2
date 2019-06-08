-- 更新‘学生选课’链接--宁德一中除外
-- 陈乐为
 update system_menu set hyperlink='/lims/timetable/student/stuCourseList' where name='学生选课' and project_name <> 'ndyzlims';