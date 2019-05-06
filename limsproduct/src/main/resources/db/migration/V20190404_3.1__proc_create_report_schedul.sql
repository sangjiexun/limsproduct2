-- 功能：生成月报报表数据（以学生为主包括教务，自主，2分批分组）
-- 作者：廖文辉
-- 日期：2019-03-13
DROP PROCEDURE if exists `proc_create_report_schedul`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_create_report_schedul`(IN termId int,IN week_s int,IN dataString varchar(255),IN t_id int)
begin
declare w int;
declare classdate datetime;

set classdate = dataString;
set w = week_s;

insert into report_schedule(term_id,term_name,academy_number,academy_name,course_no,course_name,classes,weekday,weeks,start_class,end_class,class_date,lab_name,job_no,teacher,username,student,lp_name,audit_status,ta_id,center_name,center_manager_username,center_manager_cname,lab_address,lab_room_classification,lab_room_type,plan_student_number,plan_hour,plan_hour_students)
select
st.id as term_id,
st.term_name,
sa.academy_number,
sa.academy_name,
sc.course_no,
sc.course_name,
CONCAT(scl.class_name,'-',scl.class_number,'-',scl.class_students_number) as classes,
scd.weekday,
week_s as weeks,
scd.start_class,
scd.end_class,
classdate as class_date,
lr.lab_room_name as lab_name,
teacher.username as job_no,
teacher.cname as teacher,
students.username as username,
students.cname as student,
GROUP_CONCAT(oi.lp_name SEPARATOR ',') as lp_name,
ta.status as audit_status,
ta.id as ta_id,
lb.center_name as center_name,
manager.username as center_manager_username,
manager.cname as center_manager_cname,
lr.lab_room_address as lab_address,
lr.lab_room_classification as lab_room_classification,
lr.lab_room_type as lab_room_type,
scd.plan_student_number as plan_student_number,
SUM((((tasn.end_week - tasn.start_week)+1) * ((tasn.end_class -tasn.start_class)+1) )) as plan_hour,
SUM(((((tasn.end_week - tasn.start_week)+1) * ((tasn.end_class -tasn.start_class)+1) ))*scd.plan_student_number) as plan_hour_students

from school_term st JOIN school_course sc on st.id = sc.term_id
LEFT JOIN school_academy sa on sa.academy_number = sc.academy_number
LEFT JOIN school_course_class scc on scc.course_no = sc.course_no
LEFT JOIN school_classes scl on scl.class_number = scc.class_number
LEFT JOIN school_course_detail scd on scd.course_no = sc.course_no
LEFT JOIN school_course_student scs on scs.course_detail_no = scd.course_detail_no
LEFT JOIN user students on students.username = scs.student_number
LEFT JOIN school_week sw on sw.term_id = st.id
LEFT JOIN timetable_appointment ta on ta.detail_id = scd.course_detail_no and ta.detail_id is not null
LEFT JOIN timetable_teacher_related ttr on ttr.appointment_id = ta.id
LEFT JOIN user teacher on teacher.username = ttr.teacher
left JOIN timetable_lab_related tlr on tlr.appointment_id = ta.id
LEFT JOIN lab_room lr on lr.id = tlr.lab_id
LEFT JOIN timetable_item_related tir on tir.appointment_id = ta.id
left JOIN operation_item oi on oi.id = tir.item_id
LEFT JOIN lab_center lb on lb.id=lr.lab_center_id
LEFT JOIN user manager on manager.username = lb.center_manager
JOIN timetable_appointment_same_number tasn on tasn.appointment_id = ta.id
where st.id = termId and sw.week = week_s and sw.weekday = ta.weekday
and tasn.id = t_id and sw.date = classdate and ta.`status`=1 and ta.timetable_style <> 4
GROUP BY scd.weekday,
weeks,
teacher.cname,
classdate,
students.username


union

select
tmp.term_id as term_id,
tmp.term_name as term_name,
tmp.academy_number,
tmp.academy_name,
tmp.course_no as course_no,
tmp.course_name,
null as classes,
tmp.weekday as weekday,
week_s as weeks,
tmp.start_class,
tmp.end_class,
classdate as class_date,
lr.lab_room_name as lab_name,
tmp.job_no as job_no,
tmp.teacher as teacher,
tmp.username as username,
tmp.student as student,
GROUP_CONCAT(oi.lp_name SEPARATOR ',') as lp_name,
tmp.audit_status as audit_status,
tmp.ta_id as ta_id,
lb.center_name as center_name,
manager.username as center_manager_username,
manager.cname as center_manager_cname,
lr.lab_room_address as lab_address,
lr.lab_room_classification as lab_room_classification,
lr.lab_room_type as lab_room_type,
tmp.course_count as plan_student_number,
SUM((((tmp.end_week - tmp.start_week)+1) * ((tmp.end_class -tmp.start_class)+1) )) as plan_hour,
SUM(((((tmp.end_week - tmp.start_week)+1) * ((tmp.end_class -tmp.start_class)+1) ))*tmp.course_count) as plan_hour_students

from
(select
st.id as term_id,
st.term_name,
sa.academy_number,
sa.academy_name,
sci.course_number as course_no,
sci.course_name,
ta.weekday as weekday,
week_s as weeks,
classdate as class_date,
tasn.start_class,
tasn.end_class,
tasn.id as tasn_id,
teacher.username as job_no,
teacher.cname as teacher,
students.username as username,
students.cname as student,
ta.status as audit_status,
ta.timetable_style,
ta.id as ta_id,
tsc.course_count,
tasn.start_week,
tasn.end_week

from school_term st JOIN timetable_self_course tsc on st.id = tsc.term_id
LEFT JOIN school_week sw on sw.term_id = tsc.term_id
LEFT JOIN school_academy sa on sa.academy_number = tsc.academy_number
LEFT JOIN school_course_info sci on sci.course_number = tsc.course_number
LEFT JOIN user teacher on teacher.username = tsc.teacher
LEFT JOIN timetable_course_student tcs on tcs.course_code_id = tsc.id
LEFT JOIN user students on students.username = tcs.student_number
LEFT JOIN timetable_appointment ta on ta.course_number = sci.course_number
LEFT JOIN timetable_appointment_same_number tasn on tasn.appointment_id = ta.id
where st.id = termId and sw.week = week_s and sw.weekday = ta.weekday and tasn.id = t_id and sw.date = classdate and ta.`status`=1 and ta.timetable_style <> 4
GROUP BY students.username)tmp
left JOIN timetable_lab_related tlr on tlr.appointment_id = tmp.ta_id
LEFT JOIN lab_room lr on lr.id = tlr.lab_id
LEFT JOIN timetable_item_related tir on tir.appointment_id = tmp.ta_id
left JOIN operation_item oi on oi.id = tir.item_id
LEFT JOIN lab_center lb on lb.id=lr.lab_center_id
LEFT JOIN user manager on manager.username = lb.center_manager
where tmp.term_id = termId and tmp.weeks = week_s and tmp.class_date = classdate
GROUP BY tmp.weekday,
tmp.weeks,
tmp.teacher,
tmp.student

union
select
st.id as term_id,
st.term_name,
sa.academy_number,
sa.academy_name,
sc.course_no,
sc.course_name,
CONCAT(scl.class_name,'-',scl.class_number,'-',scl.class_students_number) as classes,
scd.weekday,
week_s as weeks,
scd.start_class,
scd.end_class,
classdate as class_date,
lr.lab_room_name as lab_name,
teacher.username as job_no,
teacher.cname as teacher,
students.username as username,
students.cname as student,
GROUP_CONCAT(oi.lp_name SEPARATOR ',') as lp_name,
ta.status as audit_status,
ta.id as ta_id,
lb.center_name as center_name,
manager.username as center_manager_username,
manager.cname as center_manager_cname,
lr.lab_room_address as lab_address,
lr.lab_room_classification as lab_room_classification,
lr.lab_room_type as lab_room_type,
scd.plan_student_number as plan_student_number,
SUM((((tasn.end_week - tasn.start_week)+1) * ((tasn.end_class -tasn.start_class)+1) )) as plan_hour,
SUM(((((tasn.end_week - tasn.start_week)+1) * ((tasn.end_class -tasn.start_class)+1) ))*scd.plan_student_number) as plan_hour_students

from school_term st JOIN school_course sc on st.id = sc.term_id
LEFT JOIN school_academy sa on sa.academy_number = sc.academy_number
LEFT JOIN school_course_class scc on scc.course_no = sc.course_no
LEFT JOIN school_classes scl on scl.class_number = scc.class_number
LEFT JOIN school_course_detail scd on scd.course_no = sc.course_no
LEFT JOIN timetable_appointment ta on ta.detail_id = scd.course_detail_no and ta.detail_id is not null
LEFT JOIN timetable_teacher_related ttr on ttr.appointment_id = ta.id
LEFT JOIN timetable_appointment_group tag on tag.appointment_id = ta.id
LEFT JOIN timetable_group tg on tag.group_id = tg.id
LEFT JOIN timetable_group_students tgs on tgs.group_id = tg.id
LEFT JOIN user students on students.username = tgs.username
LEFT JOIN timetable_batch_items tbi on tbi.batch_id = tg.batch_id
LEFT JOIN school_week sw on sw.term_id = st.id
LEFT JOIN user teacher on teacher.username = ttr.teacher
left JOIN timetable_lab_related tlr on tlr.appointment_id = ta.id
LEFT JOIN lab_room lr on lr.id = tlr.lab_id
left JOIN operation_item oi on oi.id = tbi.item_id
LEFT JOIN lab_center lb on lb.id=lr.lab_center_id
LEFT JOIN user manager on manager.username = lb.center_manager
JOIN timetable_appointment_same_number tasn on tasn.appointment_id = ta.id
where st.id = termId and sw.week = week_s and sw.weekday = ta.weekday
and tasn.id = t_id and sw.date = classdate and ta.`status`=1 and ta.timetable_style = 4
GROUP BY scd.weekday,
weeks,
teacher.cname,
classdate,
students.username
;

end;;
DELIMITER ;
