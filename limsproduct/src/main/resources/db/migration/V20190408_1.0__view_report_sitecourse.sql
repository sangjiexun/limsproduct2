-- 增加视图view_report_sitecourse
-- 廖文辉 2019-3-18
drop view if exists view_report_sitecourse;
create view view_report_sitecourse AS
SELECT
	sc.course_name AS course_name,
	sc.course_number AS course_number,
	st.term_name AS term_name,
	st.id AS term_id,
	CONCAT(
		teacher.cname,
		'(',
		teacher.username,
		')'
	) AS teacherName,
	scd.total_hours AS total_hours,
	scd.theoretical_class_hour AS theoreticalHour,
	scd.experimental_class_hour AS experimental_class_hour,
	scd.course_type AS course_type,
	scd.course_type_name AS course_type_name,
	lc.center_name AS center_name,
  tmp.experimentalHour,
  tmp.projectName

FROM
	school_course sc
JOIN school_term st ON st.id = sc.term_id
LEFT JOIN school_course_info sci ON sc.course_number = sci.course_number
LEFT JOIN school_course_detail scd ON scd.course_no = sc.course_no
LEFT JOIN `user` teacher ON teacher.username = sc.teacher
LEFT JOIN school_academy sa ON sa.academy_number = sc.academy_number
LEFT JOIN lab_center lc ON lc.academy_number = sa.academy_number
LEFT JOIN (
select
mid.courseNum,
group_concat(mid.projectName SEPARATOR '<br>') AS projectName,
SUM(mid.experimentalHour) AS experimentalHour,
mid.term
from
(SELECT
sci.course_number AS courseNum,
concat(operation_item.lp_name,'(',operation_item.lp_department_hours,'h)','') AS projectName,
operation_item.lp_department_hours AS experimentalHour,
operation_item.lp_term as term
FROM
school_course_info as sci
jOIN school_course_detail scd on sci.course_number=scd.course_number
JOIN operation_item ON scd.course_number = operation_item.lp_course_number
AND scd.term_id = operation_item.lp_term
and operation_item.lp_status_check = 545
AND operation_item.lp_term >=10

GROUP BY
sci.course_number,
operation_item.lp_name,
scd.term_id )mid
GROUP BY mid.courseNum

)as tmp on tmp.courseNum = sc.course_number and tmp.term = sc.term_id
WHERE
	sc.term_id >= 10
AND scd.state = 1
GROUP BY
	st.id,
	sc.course_number