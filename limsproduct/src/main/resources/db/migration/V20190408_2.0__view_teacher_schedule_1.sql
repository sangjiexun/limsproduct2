drop view if exists `view_teacher_schedule_1`;
CREATE
ALGORITHM=UNDEFINED
DEFINER=`root`@`localhost`
SQL SECURITY DEFINER
VIEW `view_teacher_schedule_1`AS
(SELECT
tasn.id,
sc.course_no,
sci.course_name,
GROUP_CONCAT(oi.lp_name) AS item_name,
ta.weekday,
tasn.start_week,
tasn.end_week,
tasn.start_class,
tasn.end_class
FROM timetable_appointment ta
JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
JOIN school_course_info sci ON sci.course_number=ta.course_number
LEFT JOIN timetable_item_related tir ON tir.appointment_id=ta.id
LEFT JOIN operation_item oi ON oi.id=tir.item_id
JOIN school_course sc ON sc.course_no=ta.course_no
WHERE ta.timetable_style <> 7 AND ta.`status`=1
GROUP BY tasn.id
ORDER BY sc.course_no,ta.weekday,tasn.start_class)

UNION
#自主
(SELECT
tasn.id,
tsc.course_code,
sci.course_name,
GROUP_CONCAT(oi.lp_name) AS item_name,
ta.weekday,
tasn.start_week,
tasn.end_week,
tasn.start_class,
tasn.end_class
FROM timetable_appointment ta
JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
JOIN school_course_info sci ON sci.course_number=ta.course_number
LEFT JOIN timetable_item_related tir ON tir.appointment_id=ta.id
LEFT JOIN operation_item oi ON oi.id=tir.item_id
JOIN timetable_self_course tsc ON tsc.id=ta.self_course_code
WHERE ta.timetable_style <> 7 AND ta.`status`=1
GROUP BY tasn.id
ORDER BY tsc.course_code,ta.weekday,tasn.start_class)

UNION
#实验室预约
(SELECT
tasn.id,
'',
'实验室预约',
lr.lending_reason AS item_name,
ta.weekday,
tasn.start_week,
tasn.end_week,
tasn.start_class,
tasn.end_class
FROM timetable_appointment ta
JOIN timetable_appointment_same_number tasn ON tasn.appointment_id=ta.id
JOIN lab_reservation lr ON lr.timetable_id=ta.id
WHERE ta.timetable_style = 7
GROUP BY tasn.id
ORDER BY ta.weekday,tasn.start_class) ;