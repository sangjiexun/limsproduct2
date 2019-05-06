-- --------------------------------------------------------------------------------------
-- 功能：生成指定学期的老师每一天的课表数据
-- 作者：陈乐为(徐明杭添加)
-- 日期：2019-04-02
drop PROCEDURE if exists `proc_report_schedule_general_2`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_report_schedule_general_2`(IN termId int,IN week_s int,IN dataString varchar(255),IN t_id int)
begin

declare w int;
declare classdate datetime;

set classdate = dataString;
set w = week_s;

insert into report_schedule_general(term_id,term_name,academy_number,academy_name,course_no,course_name,classes,weekday,weeks,start_class,end_class,class_date,lab_name,job_no,teacher,lp_name,appointment_id)
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
oi.lp_name,
ta.id as ta_id

from school_term st JOIN school_course sc on st.id = sc.term_id
LEFT JOIN school_academy sa on sa.academy_number = sc.academy_number
LEFT JOIN school_course_class scc on scc.course_no = sc.course_no
LEFT JOIN school_classes scl on scl.class_number = scc.class_number
LEFT JOIN school_course_detail scd on scd.course_no = sc.course_no
LEFT JOIN school_week sw on sw.term_id = st.id 
LEFT JOIN timetable_appointment ta on ta.detail_id = scd.course_detail_no and ta.detail_id is not null
LEFT JOIN timetable_teacher_related ttr on ttr.appointment_id = ta.id
LEFT JOIN user teacher on teacher.username = ttr.teacher
left JOIN timetable_lab_related tlr on tlr.appointment_id = ta.id
LEFT JOIN lab_room lr on lr.id = tlr.lab_id
LEFT JOIN timetable_item_related tir on tir.appointment_id = ta.id
left JOIN operation_item oi on oi.id = tir.item_id
JOIN timetable_appointment_same_number tasn on tasn.appointment_id = ta.id
where st.id = termId and sw.week = week_s and sw.weekday = ta.weekday 
and tasn.id = t_id and sw.date = classdate and ta.`status`=1

union all

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
oi.lp_name,
ta.id as ta_id

from school_term st JOIN timetable_self_course tsc on st.id = tsc.term_id
LEFT JOIN school_academy sa on sa.academy_number = tsc.academy_number
LEFT JOIN school_course_info sci on sci.course_number = tsc.course_number
LEFT JOIN school_course sc on sc.course_number = tsc.course_number
LEFT JOIN user teacher on teacher.username = tsc.teacher
LEFT JOIN timetable_course_student tcs on tcs.course_code_id = tsc.id
LEFT JOIN school_course_class scc on scc.course_no = sc.course_no
LEFT JOIN school_classes scl on scl.class_number = scc.class_number
LEFT JOIN school_course_detail scd on scd.course_no = sc.course_no
LEFT JOIN school_week sw on sw.term_id = tsc.term_id 
LEFT JOIN timetable_appointment ta on ta.detail_id = scd.course_detail_no and ta.detail_id is not null
LEFT JOIN timetable_appointment_same_number tasn on tasn.appointment_id = ta.id
left JOIN timetable_lab_related tlr on tlr.appointment_id = ta.id
LEFT JOIN lab_room lr on lr.id = tlr.lab_id
LEFT JOIN timetable_item_related tir on tir.appointment_id = ta.id
left JOIN operation_item oi on oi.id = tir.item_id
where st.id = termId and sw.week = week_s and sw.weekday = ta.weekday 
and tasn.id = t_id and sw.date = classdate and ta.`status`=1
;

END;;
DELIMITER ;